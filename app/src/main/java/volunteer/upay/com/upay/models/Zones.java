package volunteer.upay.com.upay.models;

public class Zones {
    String id, zoneId, zoneName, contactEmail, centerHeadName, centerHeadContact, coordinatorName, coordinatorContact, numCenters, zonalOfficeAddress, numVolunteers, numStudents;

    public String getId() {
        return id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getCenterHeadName() {
        return centerHeadName;
    }

    public String getCenterHeadContact() {
        return centerHeadContact;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public String getCoordinatorContact() {
        return coordinatorContact;
    }

    public String getNumCenters() {
        return numCenters;
    }

    public String getZonalOfficeAddress() {
        return zonalOfficeAddress;
    }

    public String getNumVolunteers() {
        return numVolunteers;
    }

    public String getNumStudents() {
        return numStudents;
    }

    public Zones(String id, String zoneId, String zoneName, String contactEmail, String centerHeadName, String centerHeadContact, String coordinatorName, String coordinatorContact, String numCenters, String zonalOfficeAddress, String numVolunteers, String numStudents) {
        this.id = id;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.contactEmail = contactEmail;
        this.centerHeadName = centerHeadName;
        this.centerHeadContact = centerHeadContact;
        this.coordinatorName = coordinatorName;
        this.coordinatorContact = coordinatorContact;
        this.numCenters = numCenters;
        this.zonalOfficeAddress = zonalOfficeAddress;
        this.numVolunteers = numVolunteers;
        this.numStudents = numStudents;
    }
}
