package com.netflix.tools.koios.dto;

import java.util.List;

public class PrResponse {

    private List<PrItem> prItem;

    public List<PrItem> getPrItem() {
        return prItem;
    }

    public void setPrItem(List<PrItem> prItem) {
        this.prItem = prItem;
    }
}
