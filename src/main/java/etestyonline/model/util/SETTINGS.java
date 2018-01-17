package etestyonline.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SETTINGS {
    public static final String TEACHER = "ROLE_TEACHER";
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String ADMIN_ORG_UNIT = "ADMINS";
    public static final String DEFAULT_ORG_UNIT = "DEFAULT";
    public static final String TEACHERS_ORG_UNIT = "TEACHERS";

    public static final List<Integer> times = new ArrayList<>(Arrays.asList(5, 10, 15, 20, 30, 40, 50));
    public static final List<Integer> amounts = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
    public static final String[] ansLetters = { "A", "B", "C", "D"};

    public static final String HEAD_ADMIN_EMAIL = "admin@etestyonline.pl";

    public static final String LOG_REQUEST_FOR_USER = "Request for user ";
    public static final String LOG_DOES_NOT_EXISTS = " does not exists";
    public static final String LOG_NOT_FOUND = " not found";
    public static final String LOG_PROMOTE_TOKEN_ID = "Promote token with id ";

    public static final String QUESTION_REGEXP = "\\A[\\d\\p{L}-,_.?!:*+'\"\\s]+\\Z";

    private SETTINGS(){}
}
