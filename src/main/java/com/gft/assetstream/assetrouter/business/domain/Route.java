/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gft.assetstream.assetrouter.business.domain;

/**
 *
 * @author Dailton Santana de Almeida
 */
public enum Route {
    GENEVA(0, "out-to-geneva"), MAUI(1, "out-to-maui");
    
    private final int hint;
    private final String bindingName;
    
    Route(int hint, String bindingName) {
        this.hint = hint;
        this.bindingName = bindingName;
    }
    
    public int getHint() {
        return hint;
    }
    public String getBindingName() {
        return bindingName;
    }
}
