package ch.zhaw.mathify;

import ch.zhaw.mathify.model.User;

import java.util.List;

/**
 * Provides sample data for test cases that rely on user data.
 * Test cases can call this class and don't have to generate sample data themselves.
 */
public class UserSampleData {
    private final List<User> users = List.of(
            new User("john_doe", "jd@gmail.com", "$2a$12$r88eOP26jtTyTcPYPyhbdOEZfkHS.2cqvSoZpDRp97eLix86bZOsW"),
            new User("jane_smith", "js@mail.com", "$2a$12$GYcDJSLSQtuyyGqL3Ksuh.w14csWK5V4HIsDSiaXAhl0s4d3u2yke"),
            new User("alex_jones", "abc@xmail.com", "$2a$12$gllQ3ezTJf22FBzmAwSx6e0w0NsKVia6vetDL99TG6X4SjtOgLtfi"),
            new User("sarah_jackson", "sjackson@mail.com", "$2a$12$MS/3FWRSmL1zhc2ryLeXz.eOSSnFvMtVohuiNA05cpS/cquKnH9SW"),
            new User("michael_brown", "mail@mail.com", "$2a$12$Nu.X7I1eq4lBWa9u6soIO.OLRbTzQXpztgNvNRjn9f6ZTu1gAq26a")
    );

    public UserSampleData() {
        users.get(0).setLevel(10);
        users.get(0).setLevel(10);
        users.get(0).setExperience(55);
        users.get(1).setLevel(8);
        users.get(1).setExperience(44);
        users.get(2).setLevel(12);
        users.get(2).setExperience(33);
        users.get(3).setLevel(6);
        users.get(3).setExperience(22);
        users.get(4).setLevel(15);
        users.get(4).setExperience(11);
    }

    public List<User> getSampleUsers() {
        return users;
    }
}
