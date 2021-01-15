
import java.io.InputStream;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {


    public static void main(String[] args) throws Exception {
        Class<?> clazz = new HelloClassLoader().findClass("Hello");
        Method helloMethod = clazz.getDeclaredMethod("hello");
        helloMethod.setAccessible(true);
        helloMethod.invoke(clazz.newInstance());
    }
    @Override
    protected Class<?> findClass(String name){
        byte[] bytes = new byte[0];
        try {
            bytes = loadByte("Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] enBytes = decodeByByte(bytes);
        return defineClass(name,enBytes,0,enBytes.length);

    }

    private byte[] decodeByByte(byte[] bytes) {
        byte[] bytes1 = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes1[i] = (byte)(255 - bytes[i]);
        }
        return bytes1;
    }

    private byte[] loadByte(String name) throws Exception {
        byte[] data;

        try (InputStream fis = getClass().getResourceAsStream("./clazz/"+name + ".xlass")){
            int len = fis.available();
            data = new byte[len];
            fis.read(data);
        }
        return data;
    }
}
