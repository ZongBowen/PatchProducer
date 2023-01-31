package org.zbw.patchproducer.util;

import org.zbw.patchproducer.config.PatchProperties;

public class PropertyUtil {
    private static PatchProperties patchProperties;

    public static PatchProperties getProperties() {
        return patchProperties == null ? BeanUtil.getBean(PatchProperties.class) : patchProperties;
    }
}
