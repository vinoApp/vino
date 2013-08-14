/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vino.backend.model;

import java.util.Date;

/**
 * User: walien
 * Date: 8/4/13
 * Time: 6:25 PM
 */
public class PendingWineBottle {

    private String barcode;

    private String stickerImage;

    private Date addingDate;

    private int qty;

    public PendingWineBottle(String barcode, String stickerImage, int qty) {
        this.barcode = barcode;
        this.stickerImage = stickerImage;
        this.qty = qty;
    }

    public PendingWineBottle() {
    }

    public String getBarcode() {
        return barcode;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStickerImage() {
        return stickerImage;
    }

    public void setStickerImage(String stickerImage) {
        this.stickerImage = stickerImage;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "PendingWineBottle{" +
                "barcode='" + barcode + '\'' +
                ", stickerImage='" + stickerImage + '\'' +
                ", qty=" + qty +
                '}';
    }
}
