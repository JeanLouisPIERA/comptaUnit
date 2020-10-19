package com.dummy.myerp.consumer.dao.contrat;

import org.springframework.stereotype.Component;

/**
 * Interface du Proxy d'accès à la couche DAO
 */
@Component
public interface DaoProxy {

    /**
     * Renvoie un {@link ComptabiliteDao}
     *
     * @return {@link ComptabiliteDao}
     */
    ComptabiliteDao getComptabiliteDao();
    
    

}
