export const appCodes = {
    usernameEmpty: 'USERNAME_EMPTY',
    passwordTooShort: 'PASSWORD_TOO_SHORT',
    passwordsDontMatch: 'PASSWORDS_DONT_MATCH',
    invalidToken: 'TOKEN_INVALID',
};

export const serverCodesMapping = {
    'PASSWORD_TOO_SHORT': appCodes.passwordTooShort,
    'PASSWORDS_DONT_MATCH':appCodes.passwordsDontMatch,
    'INVALID_RESET_PASSWORD_TOKEN':appCodes.invalidToken,
};
