
package Logica;

import Datos.Archivo;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_COLOR;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author CristianAG
 */
public class OpenCV {
    
    private double [][] matriz;
//    Matriz objMatriz;
    ArrayList<double []> matrix;
    ArrayList<int []> m;
    Archivo objArchivo;
    
    
    public ArrayList<String> archivosInternos()
    {
        ArrayList<String> ruta = listarArchivos();
        System.out.println("ruta "+ruta.get(0));
        int i;
        String path="entrenar\\";
        ArrayList<String> vect = new ArrayList();
        File f ;
        for(i=0;i<ruta.size();i++)
        {
            path="entrenar\\";
            path = path+"\\"+ruta.get(i)+"\\";
            f= new File(path);
            if(f.exists())
            {
                File [] archivos = f.listFiles();
//                System.out.println("f: "+archivos[1]);
                for(int j=0;j<archivos.length;j++)
                {
                    vect.add(path+(archivos[j].getName()));
//                    System.out.println(vect.get(j));
                }
            }
       
        }
        return vect;
    }
    
    public ArrayList<String> listarArchivos()
    {
       int i;
       ArrayList<String> result = new ArrayList();
       String path;
       path="entrenar\\";
       File f  =new File(path);
       if(f.exists())
       {
           File [] archivos = f.listFiles();
//           System.out.println("arc "+archivos[0].getName());
           for(i=0;i<archivos.length;i++)
           {
               result.add(archivos[i].getName());
           }           
       }
       return result;
    }
    
    public void digitalizar(){

        ArrayList<double []> matriz =new   ArrayList<double []>(); 
        ArrayList<String> vector=archivosInternos();
        m = new ArrayList<int []>();
        System.out.println("tam "+vector.size());
        for(int i=0;i<vector.size();i++)
        {
           matriz.add(iniciar(vector.get(i)));           
        }
//        System.out.println("matr "+matriz.size());
      this.matrix=matriz;
      convertirMatriz();
       try
       {
           crearArchivoEntrenamiento();
       }
       catch (IOException ex) 
       {
           Logger.getLogger(OpenCV.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public double [] iniciar(String ruta) {
        //System.out.println(ruta);
        double[] vector;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mRgba = Imgcodecs.imread(ruta,CV_LOAD_IMAGE_COLOR);
        vector =imprimir(binaria(mRgba));
//        System.out.println("vector: "+vector[100]);
        
       return vector;
    }
    
    public static Mat binaria(Mat imagen_original_mat)
    {
        Mat gris=new Mat(imagen_original_mat.width(),imagen_original_mat.height(),imagen_original_mat.type());
        Mat binario = new Mat(imagen_original_mat.width(),imagen_original_mat.height(),imagen_original_mat.type());
        Imgproc.cvtColor(imagen_original_mat, gris, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(gris, binario, 100, 255, Imgproc.THRESH_BINARY);
        return binario;
    }
    
    public  double[] imprimir (Mat imagen)
    {
        double [] vector;
        matriz = new double[imagen.height()][imagen.width()];
        //MatOfByte matOfByte = new MatOfByte();
        //Imgcodecs.imencode(".jpg", imagen, matOfByte);
        for(int i =0;i<imagen.height();i++){
          for(int j=0;j<imagen.width();j++){
             double data[]=imagen.get(i, j);
             if(data[0]>0)
             {   
                 matriz[i][j]=0.0;
             }else{
              matriz[i][j]=1.0;
             }
          }
          
        }
        double mn[][]=reducir(matriz);
        //mn=reducir(mn);
        //impMAtriz(mn);
        //objMatriz=new Matriz(mn);
        vector=vectorCaracteristico(mn);
        //impVector(vector);
        return vector;
    }
    
    public void convertirMatriz()
    {
        int i;

        for(i=0;i<this.matrix.size();i++)
        {
            int  []v= new int [this.matrix.get(i).length];
            int j = this.matrix.get(i).length;
            for(int k=0;k<j;k++)
            {
                v[k]=(int) this.matrix.get(i)[k];
            }
            this.m.add(v);
        }
    }
    
    public double[][] reducir(double [][] m){
        int tam=m.length/2;
        double [][] n = new double [tam][tam];
        int k=0;
        for(int i=0;i<m.length;i=i+2){
            int l=0;
            for(int j=0;j<m.length;j=j+2){
               double t=(m[i][j]+m[i+1][j]+m[i][j+1]+m[i+1][j+1])/4;
               if(t==0){
                n[k][l]=0;
               }else{
                  n[k][l]=1;
               }
               
               l++;
            }
            k++;
          }
        return n;
      }
    
    public double[] vectorCaracteristico(double [][] m)
    {
        double [] vect = new double[m.length];
        
        for(int i=0;i<m.length;i++)
        {
            vect[i] = m[i][i];
        }
        return vect;
    }
    
    public void crearArchivoEntrenamiento() throws RemoteException, IOException
    {
        objArchivo = new Archivo();
        ArrayList<String> internos = agragarUsuarioPerteneciente();

        int var = objArchivo.ExistenciaArchivo("Entrenamiento.txt");
        if(var==1)
        {
            objArchivo.EliminarArchivo("Entrenamiento.txt");
            objArchivo.crearTxt("Entrenamiento.txt");
        }
        else
        {
            objArchivo.crearTxt("Entrenamiento.txt");
        }
    
        for(int i=0; i<this.m.size();i++)
        {
            //System.out.println(internos.get(i));
            //impVector(this.matrix.get(i));
            String entrada ="";
            int k = m.get(i).length;
            for(int j=0;j<k;j++)
            {
                //System.out.println(internos.get(i));
                //System.out.println("----------------------");
                //System.out.println(internos.get(j));
                 entrada= entrada+String.valueOf(m.get(i)[j]+";");
            }
            entrada=entrada+internos.get(i);
            objArchivo.abrirArchivo("Entrenamiento.txt", true);
            objArchivo.escribirArchivo(entrada);
            objArchivo.cerrarArchivo();
        }
    }
    
    public ArrayList<String> agragarUsuarioPerteneciente()
    {
        ArrayList<String> vec = NombreCarpetaImagen();
        ArrayList<String> result = new ArrayList();
        String a ;
        for(int i=0;i<vec.size();i++)
        {
            a = vec.get(i);
            a =a.replaceAll("", ";");
            a=a.replaceFirst(";", "");
            //a="|"+a;
            result.add(a);
        }
         for(int i=0;i<result.size();i++)
        {
            System.out.println(result.get(i));
        }
        return result;
    }
    
    public ArrayList<String> NombreCarpetaImagen()
    {
        ArrayList<String> result =archivosInternos();
        ArrayList<String> Archivos = new ArrayList();
        String palabra;
        for(int i=0; i<result.size();i++)
        {
            palabra =result.get(i);
            String vector[] = palabra.split("\\\\");
            Archivos.add(vector[2]);
        }
        return Archivos;
    }
    
}
