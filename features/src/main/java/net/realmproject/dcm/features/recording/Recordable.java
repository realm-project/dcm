package net.realmproject.dcm.features.recording;


import java.io.Serializable;


/**
 * Interface to request/hint that something should be recorded. The return value
 * of isRecordRequested is non-binding. Entities may be recorded even without a
 * request. Entities which request recording may be ignored if there is no
 * recorder configured, or of the recorder chooses to ignore the request.
 * 
 * @author NAS
 *
 */
public interface Recordable extends Serializable {

    /**
     * Indicates if this command should be recorded (if such facilities are
     * available)
     */
    boolean isToRecord();

    /**
     * Specifies if this command should be recorded (if such facilities are
     * available)
     */
    void setToRecord(boolean toRecord);

}
