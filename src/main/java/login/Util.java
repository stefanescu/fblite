package login;

import javafx.scene.control.Alert;

public class Util
{
    public static void print(String s)
    {
        System.out.println(s);
    }
    
    public static void init(String[] a)
    {
        for(int x = 0; x < a.length ; x++)
        {
            a[x] = "";
        }
    }
    
//    public static void init(Profile[] a)
////    {
////        for(int x = 0; x < a.length ; x++)
////        {
////            a[x] = null;
////        }
////    }
    
    public static void print(String[] arr)
    {
        for (int x = 0 ; x < arr.length ; x++)
        {
            if (arr[x].equals(""))
            {
                break;
            }
            
            System.out.println(arr[x]);
        }
    }

    public static void showErrorDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    public static void showInfoDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }


}