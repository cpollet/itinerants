import React from 'react';
import Alert from '../../../widgets/Alert';
import Form from '../../../widgets/Form';
import FormRow from '../../../widgets/FormRow';
import FormLabel from '../../../widgets/FormLabel';
import FormField from '../../../widgets/FormField';
import TextInput from '../../../widgets/TextInput';
import PasswordInput from '../../../widgets/PasswordInput';
import Button from '../../../widgets/Button';
import {FormContainer} from '../../../lib/form/FormContainer';
import FormFieldContainer from '../../../lib/form/FormFieldContainer';
import styles from './ResetPassword.less';

class ResetPassword extends React.Component {
    render() {
        return (
            <div>
                <h2>Réinitialisation du mot de passe</h2>

                {!this.props.passwordsMatch && <Alert type="error" text="Les mots de passe ne correspondent pas."/>}
                {this.props.passwordTooShort && <Alert type="error" text="Le mot de passe est trop court."/>}
                {this.props.usernameEmpty && <Alert type="error" text="Le nom d'utilisateur est vide."/>}
                {
                    this.props.tokenNotValid &&
                    <Alert type="error">
                        Le lien de modification de mot de passe n'est pas valide.&nbsp;
                        <a href="#" onClick={(e) => {
                            this.props.sendNewToken(this.props.username);
                            e.preventDefault();
                        }}>Renvoyer</a> le lien.
                    </Alert>
                }
                {
                    this.props.resetPasswordTokenSent &&
                    <Alert type="success"
                           text="Un email content le lien de réinitialisation du mot de passe a été envoyé."/>
                }

                <FormContainer name="resetPassword"
                               onSubmit={(e, data) => {
                                   if (typeof data !== 'undefined') {
                                       this.props.send(data.username, data.password1, data.password2, this.props.token);
                                   }
                                   e.preventDefault();
                               }}
                               initialValues={{username: '', password1: '', password2: ''}}
                >
                    <Form>
                        <FormRow>
                            <FormLabel>Nom d'utilisateur</FormLabel>
                            <FormField>
                                <FormFieldContainer name="username">
                                    <TextInput placeholder="Nom d'utilisateur"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel>Mot de passe</FormLabel>
                            <FormField>
                                <FormFieldContainer name="password1">
                                    <PasswordInput placeholder="Mot de passe"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel>Mot de passe (vérification)</FormLabel>
                            <FormField>
                                <FormFieldContainer name="password2">
                                    <PasswordInput placeholder="Mot de passe (vérification)"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <div className={styles.buttonsRow}>
                                <Button type="primary" submit={true}>Modifier</Button>
                            </div>
                        </FormRow>
                    </Form>
                </FormContainer>
            </div>
        );
    }
}

ResetPassword.propTypes = {
    send: React.PropTypes.func.isRequired,
    sendNewToken: React.PropTypes.func.isRequired,
    token: React.PropTypes.string,
    passwordsMatch: React.PropTypes.bool.isRequired,
    passwordTooShort: React.PropTypes.bool.isRequired,
    tokenNotValid: React.PropTypes.bool.isRequired,
    usernameEmpty: React.PropTypes.bool.isRequired,
    username: React.PropTypes.string,
    resetPasswordTokenSent: React.PropTypes.bool.isRequired,
};

export default ResetPassword;
