package com.thingnoy.thingnoy500v3.api;

import com.thingnoy.thingnoy500v3.api.base.BaseService;

/**
 * Created by HBO on 4/4/2561.
 */

public class UdonService extends BaseService<ApiService> {

    public static UdonService newInstance(String baseUrl) {
        UdonService service = new UdonService();
        service.setBaseUrl(baseUrl);
        return service;
    }

    private UdonService() {
    }

    @Override
    protected Class<ApiService> getApiClassType() {
        return ApiService.class;
    }
}
