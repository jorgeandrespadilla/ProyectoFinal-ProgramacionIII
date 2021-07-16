package infraestructura;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.util.UUID;

/**
 *
 * @author Jorge Padilla
 */
public class ImgStorage {

    private final String rootDir = "src/";
    private static ImgStorage instance;
    private final Image missingImg;
    private final HashMap<String, Image> store;
    private File temp;

    private ImgStorage(String path) {
        path = normalizePath(path);
        store = new HashMap<>();
        this.missingImg = new Image("assets/missing-img.png");
        initStore(path);
    }

    public static ImgStorage getInstance() {
        if (instance == null) {
            String path = "src/assets/menu";
            instance = new ImgStorage(path);
        }
        return instance;
    }

    public Image getMissingImg() {
        return this.missingImg;
    }
    
    public boolean hasTemp() {
        return temp != null;
    }

    public Image chooseImage(Stage parent) {
        FileChooser fileChooser = new FileChooser();
        setupFileChooser(fileChooser);
        this.temp = fileChooser.showOpenDialog(parent);

        if (temp != null) {
            try {
                return convertFileToFxImage(temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String saveImage() throws Exception {
        if (temp == null) {
            throw new Exception("Una imagen debe ser escogida antes de guardarse en el almacenamiento.");
        }
        String name = UUID.randomUUID() + "_" + temp.getName();
        store.put(name, convertFileToFxImage(temp));

        return name;
    }

    public void clearTemp() {
        temp = null;
    }

    public Image getImage(String name) {
        name = normalizePrefix(name);
        return store.getOrDefault(name, missingImg);
    }

    public void deleteImage(String name) {
        name = normalizePrefix(name);
        store.remove(name);
    }

    private void initStore(String path) {
        File root = new File(rootDir + path);
        String[] files = root.list();
        for (String file : files) {
            Image image = new Image(path + "/" + file); //Must not include "src/"
            store.put(file, image);
        }
    }

    private void setupFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Seleccione una imagen");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagen", "*.png", "*.jpg", "*.jpeg")
        );
    }

    private String normalizePrefix(String path) {
        path = path.trim();
        if (path.indexOf("/") == 0) {
            path = path.substring(1);
        }
        return path;
    }

    private String normalizePath(String path) {
        normalizePrefix(path);
        Pattern p = Pattern.compile("^" + rootDir);
        if (p.matcher(path).find()) {
            path = path.substring(4);
        }
        if (path.lastIndexOf("/") == path.length() - 1) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    private Image convertBufferedToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    private Image convertFileToFxImage(File file) throws IOException {
        BufferedImage bi = ImageIO.read(file);
        return convertBufferedToFxImage(bi);
    }
}
