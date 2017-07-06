package hr.fer.android.hw0036487720.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class used as a model that encapsulates JSON data that is fetched.
 */


public class FormResponse implements Serializable {
    @SerializedName("avatar_location")
    private String avatarLocation;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("email_sknf")
    private String email_sknf;

    @SerializedName("spouse")
    private String spouse;

    @SerializedName("age")
    private String age;


    /**
     * Instantiates a new form response from given arguments.
     *
     * @param avatarLocation the avatar location
     * @param firstName      the first name
     * @param lastName       the last name
     * @param phoneNo        the phone no
     * @param email_sknf     the email sknf
     * @param spouse         the spouse
     * @param age            the age
     */
    public FormResponse(String avatarLocation, String firstName, String lastName, String phoneNo, String email_sknf, String spouse, String age) {
        this.avatarLocation = avatarLocation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email_sknf = email_sknf;
        this.spouse = spouse;
        this.age = age;
    }

    /**
     * Gets avatar location.
     *
     * @return the avatar location
     */
    public String getAvatarLocation() {
        return avatarLocation;
    }


    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Gets phone no.
     *
     * @return the phone no
     */
    public String getPhoneNo() {
        return phoneNo;
    }


    /**
     * Gets email sknf.
     *
     * @return the email sknf
     */
    public String getEmail_sknf() {
        return email_sknf;
    }


    /**
     * Gets spouse.
     *
     * @return the spouse
     */
    public String getSpouse() {
        return spouse;
    }


    /**
     * Gets age.
     *
     * @return the age
     */
    public String getAge() {
        return age;
    }

}
