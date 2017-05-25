package net.cpollet.itinerants.core.domain.data;

/**
 * Created by cpollet on 13.02.17.
 */
public interface PersonData {
    PersonData EMPTY = new PersonData() {
        @Override
        public String getUUID() {
            return "";
        }

        @Override
        public String getFirstName() {
            return "";
        }

        @Override
        public void setFirstName(String firstName) {
            // do nothing
        }

        @Override
        public String getLastName() {
            return "";
        }

        @Override
        public void setLastName(String lastName) {
            // do nothing
        }

        @Override
        public String getUsername() {
            return "";
        }

        @Override
        public String getPassword() {
            return "";
        }

        @Override
        public void setPassword(String s) {
            // do nothing
        }

        @Override
        public String getEmail() {
            return "";
        }

        @Override
        public void setEmail(String email) {
            // no nothing
        }

        @Override
        public String getRoles() {
            return "";
        }

        @Override
        public float getTargetRatio() {
            return 0;
        }

        @Override
        public void availableFor(EventData eventData) {
            // no nothing
        }
    };

    String getUUID();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getUsername();

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

    String getRoles();

    float getTargetRatio();

    void availableFor(EventData eventData);
}
