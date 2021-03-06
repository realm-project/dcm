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
     * 
     * @return true if the recording flat is setto true, false otherwise
     */
    boolean isToRecord();

    /**
     * Specifies if this command should be recorded (if such facilities are
     * available)
     * 
     * @param toRecord
     *            flag to specify the recording preference
     */
    void setToRecord(boolean toRecord);

    /**
     * Convenience/alternate form of setToRecord
     * 
     * @param toRecord
     *            flag to specify the recording preference
     */
    default void setRecord(boolean toRecord) {
        setToRecord(toRecord);
    }

}
