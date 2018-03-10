package io.cess.comm.tcp;

/**
 * @author lin
 * @date 1/26/16.
 */
public class EmptyPackage extends ResponsePackage {
    @Override
    public byte getType() {
        return (byte)255;
    }

    @Override
    public byte[] write() {
        return new byte[0];
    }
}
