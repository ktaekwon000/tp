package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.ColorTag;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the CliniCal application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final ProfilePicture profilePicture;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final ColorTag colorTag;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, ProfilePicture profilePicture,
                   ColorTag colorTag) {
        requireAllNonNull(name, phone, email, address, tags, colorTag);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.profilePicture = profilePicture;
        this.colorTag = colorTag;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public ColorTag getColorTag() {
        return colorTag;
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName())
                && (otherPatient.getPhone().equals(getPhone()) || otherPatient.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getEmail().equals(getEmail())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getTags().equals(getTags())
                && otherPatient.getProfilePicture().equals(getProfilePicture())
                && otherPatient.getColorTag().equals(getColorTag());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, profilePicture, colorTag);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" ColorTag: ")
                .append(getColorTag())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
