package main.java.polarization;

import net.sf.tweety.arg.dung.semantics.Extension;

import java.util.Collection;

public class WeightedJaccardIndex implements ExtensionBasedPolarizationMetric{
    @Override
    public double getPolarization(Collection<Extension> exts) {
        return 0;
    }

    @Override
    public double getPolarization(Extension ext1, Extension ext2) {
        return 0;
    }
}
