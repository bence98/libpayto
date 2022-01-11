package hu.agyklub.csokicraft.libpayto.objects;

import java.net.URI;

class AbstractPaytoBicData extends AbstractPaytoData{
    public String bic;

    public AbstractPaytoBicData(URI uri) {
        super(uri);
    }
}
