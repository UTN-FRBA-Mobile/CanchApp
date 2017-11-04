package com.santiago.canchaapp.app.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ListView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import com.github.clans.fab.FloatingActionMenu;
import com.santiago.canchaapp.app.adapter.page.GridFotosAdapter;
import com.santiago.canchaapp.dominio.ImageItem;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.CountDownTimer;

public class CargarFotosCanchaFragment extends Fragment {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView setImage;
    private LinearLayout mRlView;
    private String mPath;

    private GridView gridView;
    private ArrayList<File> fotosList;

    private ArrayList<ImageView> itemList;
    ArrayAdapter<ImageView> adapter;
    ListView lv;

    FloatingActionMenu actionMenu;
        com.github.clans.fab.FloatingActionButton fbutton1, fbutton2, fbutton3;

    public static CargarFotosCanchaFragment nuevaInstancia() {
        /*CargarFotosCanchaFragment fragment = new CargarFotosCanchaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHAS, (Serializable) datosDeCanchas());
        fragment.setArguments(args);*/
        return new CargarFotosCanchaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cargar_fotos_de_cancha, container, false);
        ButterKnife.bind(this, view);

        actionMenu = view.findViewById(R.id.fBtnMenu);
        actionMenu.setClosedOnTouchOutside(true);

        fbutton1 = actionMenu.findViewById(R.id.fBtnTomarFoto);
        fbutton2 = actionMenu.findViewById(R.id.fBtnSeleccionarImagen);
        fbutton3 = actionMenu.findViewById(R.id.fBtnGuardar);
        setImage = view.findViewById(R.id.ImageView01);
        gridView = view.findViewById(R.id.gridViewImages);

        lv = view.findViewById(R.id.ListView);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<ImageView>(getActivity(), android.R.layout.simple_list_item_multiple_choice, itemList);
        lv.setAdapter(adapter);

        if(mayRequestStoragePermission()){
            fbutton1.setEnabled(true);
            fbutton2.setEnabled(true);
        } else{
            fbutton1.setEnabled(false);
            fbutton2.setEnabled(false);
        }

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView parent, View view,int position, long id){
                SparseBooleanArray positionChecker = lv.getCheckedItemPositions();
                int count = lv.getCount();
                for(int item = count -1; item >= 0; item--){
                    if(positionChecker.get(item)){
                        adapter.remove(itemList.get(item));
                    }
                }
                positionChecker.clear();
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        fbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openCamera(PHOTO_CODE);
            }
        });

        View.OnClickListener addlistener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Por favor, seleccioná app de imagen:"), SELECT_PICTURE);
            }
        };

        fbutton2.setOnClickListener(addlistener);

        fbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubMenuGuardar();
                abrirFragmentSiguiente();
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cargar fotos de cancha");

        return view;
    }

    class GridAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return fotosList.size();
        }

        @Override
        public Object getItem(int position){
            return fotosList.get(position);
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto_cancha, parent, false);
            ImageView imageView = convertView.findViewById(R.id.foto_cancha_item);
            imageView.setImageURI(Uri.parse(getItem(position).toString() ));
            return convertView;
        }
    }

    ArrayList<File> imageReader(File root){
        ArrayList<File> lista= new ArrayList<>();

        File[] archivos= root.listFiles();

        for(int i= 0; i<archivos.length; i++){
            if(archivos[i].isDirectory()){
                lista.addAll(imageReader(archivos[i]));
            }else{
                if(archivos[i].getName().endsWith(".jpg")){
                    lista.add(archivos[i]);
                }
            }
        }
        return lista;
    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        /*if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;*/

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "@string/txtDialogExplanationPermisos1",
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
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, opcionCamera);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    //@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.dualPane);
          fragment.onActivityResult(requestCode, resultCode, data);*/

        //onActivityResult(requestCode, resultCode, data);
        //getActivity().onActivityReenter(resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    setLocationImage(mPath);
                    setImageButton(mPath, setImage);
                    break;
                case SELECT_PICTURE:
                    Uri path1 = data.getData();
                    setImage.setImageURI(path1);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setLocationImage(String mPath) {
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

    private void setImageButton(String mPath, ImageView unaImageView) {
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
                //Uri uri = Uri.fromParts("package", getPackageName(), null);
                //intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("@string/txtAbortarPermisos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish();
            }
        });

        builder.show();
    }

    public void clickSubMenuGuardar(){
        Toast.makeText(getActivity().getApplicationContext(),"Tu Cancha ha sido guardada correctamente.", Toast.LENGTH_LONG).show();
    }

    private void abrirFragmentSiguiente() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Datos del Club");
    }
}
