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
        public String getLastName() {
            return "";
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

    String getLastName();

    String getUsername();

    String getPassword();

    void setPassword(String password);

    String getEmail();

    String getRoles();

    float getTargetRatio();

    void availableFor(EventData eventData);
}
