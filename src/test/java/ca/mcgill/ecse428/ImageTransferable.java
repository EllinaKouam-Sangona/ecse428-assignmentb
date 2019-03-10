package ca.mcgill.ecse428;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

final class ImageTransferable implements Transferable {
    final BufferedImage image;

    public ImageTransferable(final BufferedImage image) {
        this.image = image;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] {DataFlavor.imageFlavor};
    }

    public boolean isDataFlavorSupported(final DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            return image;
        }

        throw new UnsupportedFlavorException(flavor);
    }
};