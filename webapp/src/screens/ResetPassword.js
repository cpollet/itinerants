import React from 'react';
import Alert from '../components/core/Alert';
import Form from '../components/core/Form';
import FormRow from '../components/core/FormRow';
import FormLabel from '../components/core/FormLabel';
import FormField from '../components/core/FormField';
import TextInput from '../components/core/TextInput';
import PasswordInput from '../components/core/PasswordInput';
import Button from '../components/core/Button';
import {FormContainer} from '../lib/form/FormContainer';
import FormFieldContainer from '../lib/form/FormFieldContainer';
import styles from './ResetPassword.less';

class ResetPassword extends React.Component {
    render() {
        return (
            <div>
                <h2>Réinitialisation du mot de passe</h2>

                {!this.props.passwordsMatch && <Alert type="error" text="Les mots de passe ne correspondent pas."/>}
                {this.props.passwordTooShort && <Alert type="error" text="Le mot de passe est trop court."/>}
                {this.props.tokenNotValid && <Alert type="error" text="Le lien de modification de mot de passe n'est pas valide."/>}
                {this.props.usernameEmpty && <Alert type="error" text="Le nom d'utilisateur est vide."/>}

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
    token: React.PropTypes.string,
    passwordsMatch: React.PropTypes.bool.isRequired,
    passwordTooShort: React.PropTypes.bool.isRequired,
    tokenNotValid: React.PropTypes.bool.isRequired,
    usernameEmpty: React.PropTypes.bool.isRequired,
};

export default ResetPassword;
