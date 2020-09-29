package com.dummy.myerp.technical.exception;

import org.junit.jupiter.params.aggregator.ArgumentAccessException;

/**
 * Classe des Exception de type "Donnée non trouvée"
 * **************************************************************************ERREUR DE CODE
 * Le code proposé pour la création de la classe est erroné "public class NotFoundException extends Exception"
 * NotFoundException devrait étendre java.lang.object NotFoundException : cette rédaction est impossible 
 * Il s'agit de ClientErrorException
 */
public class NotFoundException extends ArgumentAccessException {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur.
     * * **************************************************************************ERREUR DE CODE
     * Constructeur inexistant pour cette exception
 
    public NotFoundException() {
        super();
    }
    */

    /**
     * Constructeur.
     * @param pMessage -
    */ 
    public NotFoundException(String pMessage) {
        super(pMessage);
    }
   

    /**
     * Constructeur.
     * * **************************************************************************ERREUR DE CODE
     *Constructeur inexistant pour cette exception
     * @param pCause -
     
    public NotFoundException(Throwable pCause) {
        super(pCause);
    }
	*/

    /**
     * Constructeur.
     *
     * @param pMessage -
     * @param pCause -
     */
    public NotFoundException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
