package com.santiago.canchaapp.app.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import android.support.design.widget.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.FotosCanchaAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Storage;
import com.santiago.canchaapp.dominio.TipoCancha;
import com.santiago.canchaapp.dominio.TipoSuperficie;
import com.santiago.canchaapp.servicios.Sesion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.util.Collections.singletonList;

public class CargarFotosCanchaFragment extends Fragment {

    private static String APP_DIRECTORY = "Pictures/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private String mPath;
    private Uri mCameraTempUri;

    private GridView gridview;
    private FotosCanchaAdapter fotosCanchaAdapter;

    FloatingActionMenu actionMenu;
        com.github.clans.fab.FloatingActionButton fbutton1, fbutton2;

    @BindView(R.id.fBtnGuardar)
    public FloatingActionButton fbuttonGuardar;

    public static CargarFotosCanchaFragment nuevaInstancia() {
        return new CargarFotosCanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cargar_fotos_de_cancha, container, false);

        actionMenu = view.findViewById(R.id.fBtnMenu);
        actionMenu.setClosedOnTouchOutside(true);

        fbutton1 = actionMenu.findViewById(R.id.fBtnTomarFoto);
        fbutton2 = actionMenu.findViewById(R.id.fBtnSeleccionarImagen);

        gridview = view.findViewById(R.id.grid_view_fotos_cancha);
        fotosCanchaAdapter = new FotosCanchaAdapter(view.getContext());
        gridview.setAdapter(fotosCanchaAdapter);

        ButterKnife.bind(this, view);
        fbuttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubMenuGuardar();
                abrirFragmentSiguiente();
            }
        });

        boolean enabled = mayRequestStoragePermission(view);
        fbutton1.setEnabled(enabled);
        fbutton2.setEnabled(enabled);

        fbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(PHOTO_CODE);
            }
        });

        View.OnClickListener addlistener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                startActivityForResult(Intent.createChooser(intent, "Por favor, seleccioná app de imagen:"), SELECT_PICTURE);
            }
        };

        fbutton2.setOnClickListener(addlistener);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cargar fotos de cancha");

        return view;
    }

    private boolean mayRequestStoragePermission(View view) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((ActivityCompat.checkSelfPermission(getContext(),WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(getContext(),CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(view, R.string.txtDialogExplanationPermisos1,
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void openCamera(final int opcionCamera) {
        //Comprueba si existe cámara física.
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getActivity(), "Este dispositivo no tiene cámara", Toast.LENGTH_SHORT).show();
            return;
        }

          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
          String date = dateFormat.format(new Date());
          String imageName = date + ".jpg";

          ContentValues values = new ContentValues(1);
          values.put(MediaStore.Images.Media.MIME_TYPE, imageName);
          mCameraTempUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraTempUri);
          intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          startActivityForResult(intent, opcionCamera);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    //@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            List<Uri> selectedImages = new ArrayList<>();
            switch (requestCode){
                case PHOTO_CODE:
                    selectedImages.add(mCameraTempUri);
                    fotosCanchaAdapter.agregarFotos(selectedImages);
                    break;
                case SELECT_PICTURE:
                    if (data.getClipData() == null) { // Galería deja seleccionar 1 sola foto
                        fotosCanchaAdapter.agregarFotos(singletonList(data.getData()));
                    } else { // Galería deja seleccionar más de una foto
                        ClipData clipData = data.getClipData();

                        for (int image = 0; image < clipData.getItemCount(); image++) {
                            Uri path = clipData.getItemAt(image).getUri();
                            selectedImages.add(path);
                        }
                        fotosCanchaAdapter.agregarFotos(selectedImages);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setLocationImage(String mPath) { //Scan el archivo en la ubicacion y log la URL en el dispositivo.
        MediaScannerConnection.scanFile(getActivity().getApplicationContext(),
                new String[]{mPath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> Uri = " + uri);
                    }
                });
    }

    private void setImage(String mPath, ImageView unaImageView) {
        Bitmap bitmap = BitmapFactory.decodeFile(mPath);
        unaImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity().getApplicationContext(), "Permisos aceptados", Toast.LENGTH_SHORT).show();
                fbutton1.setEnabled(true);
                fbutton2.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setTitle("@string/txtTituloPermisos");
        builder.setMessage("@string/txtDialogExplanationPermisos2");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("@string/txtAbortarPermisos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });

        builder.show();
    }

    public void clickSubMenuGuardar(){
        Toast.makeText(getActivity().getApplicationContext(),"Tu Cancha ha sido guardada correctamente.", Toast.LENGTH_LONG).show();
    }

    private void abrirFragmentSiguiente() {
        subirDatos();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mi Club");
    }

    private void subirDatos() {
        // Datos generales
        final String idCancha = UUID.randomUUID().toString();
        final String idClub = Sesion.getInstancia().getUsuario().getIdClub();
        final List<Uri> fotos = fotosCanchaAdapter.getFotos();
        final int cantidadFotos = fotos.size();

        final boolean[] exito = new boolean[1]; exito[0] = true;
        final int[] subidas = new int[1]; subidas[0] = 0;
        final String[] urls = new String[cantidadFotos];

        if (cantidadFotos == 0) {
            subirCancha(idClub, idCancha, new ArrayList<String>());
            return;
        }

        // Subir fotos al storage de Firebase
        for (int f = 0; f < cantidadFotos; f++) {
            final int imagenActual = f;
            UploadTask upload = Storage.getInstancia().subirFotoCancha(idClub, idCancha, fotos.get(f), f);
            upload.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    if (exito[0]) {
                        exito[0] = false;
                        Toast.makeText(getContext(), R.string.txtErrorSubiendoFotos, Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    urls[imagenActual] = downloadUrl.toString();
                    subidas[0] = subidas[0] + 1;
                    if (subidas[0] == cantidadFotos) {
                        // Todas las fotos están cargadas => subir cancha
                        subirCancha(idClub, idCancha, Arrays.asList(urls));
                    }
                }
            });
        }
    }

    private void subirCancha(String idClub, String idCancha, List<String> fotos) {
        Cancha cancha = new Cancha(UUID.fromString(idCancha),
                getArguments().getString("nombreCancha"),
                TipoCancha.deNombre(getArguments().getString("deporte")),
                TipoSuperficie.deNombre(getArguments().getString("superficie")),
                getArguments().getBoolean("opcTechada"),
                getArguments().getInt("precio"),
                fotos,
                UUID.fromString(idClub),
                Sesion.getInstancia().getUsuario().getHorarioClub()); // TODO usar horario del club
        DataBase.getInstancia().insertCancha(cancha.getIdClub(), cancha);
    }

}
