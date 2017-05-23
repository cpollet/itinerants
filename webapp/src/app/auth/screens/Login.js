import React from 'react';
import Form from '../../../widgets/Form';
import FormRow from '../../../widgets/FormRow';
import FormLabel from '../../../widgets/FormLabel';
import FormField from '../../../widgets/FormField';
import TextInput from '../../../widgets/TextInput';
import PasswordInput from '../../../widgets/PasswordInput';
import Button from '../../../widgets/Button';
import {FormContainer} from '../../../lib/form/FormContainer';
import FormFieldContainer from '../../../lib/form/FormFieldContainer';
import Alert from '../../../widgets/Alert';
import {renderIf} from '../../../lib/helpers';
import styles from './Login.less';

class Login extends React.Component {
    render() {
        return (
            <div>
                <h2>Connection</h2>
                {
                    this.props.invalidCredentials &&
                    <Alert type="error">
                        Nom d'utilisateur ou mot de passe invalide.&nbsp;
                        <a href="#" onClick={(e) => {
                            this.props.sendPasswordResetToken(this.props.username);
                            e.preventDefault();
                        }}>Mot de passe oublié ?</a>
                    </Alert>
                }
                {renderIf(this.props.loginExpired,
                    <Alert type="info" text="Session expirée."/>)}
                {
                    this.props.resetPasswordTokenSent &&
                    <Alert type="success">
                        Un email content le lien de réinitialisation du mot de passe a été envoyé.
                    </Alert>
                }

                <FormContainer name="login"
                               onSubmit={(e, data) => {
                                   if (typeof data !== 'undefined') {
                                       this.props.login(data.username, data.password);
                                   }
                                   e.preventDefault();
                               }}
                               initialValues={{username: this.props.rememberMeUsername}}>
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
                                <FormFieldContainer name="password">
                                    <PasswordInput placeholder="Mot de passe"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <div className={styles.buttonsRow}>
                                <Button type="primary" submit={true}>Connexion</Button>
                            </div>
                        </FormRow>
                    </Form>
                </FormContainer>
            </div>
        );
    }
}

Login.propTypes = {
    login: React.PropTypes.func.isRequired,
    invalidCredentials: React.PropTypes.bool,
    loginExpired: React.PropTypes.bool,
    rememberMeUsername: React.PropTypes.string,
    sendPasswordResetToken: React.PropTypes.func.isRequired,
    username: React.PropTypes.string,
    resetPasswordTokenSent: React.PropTypes.bool.isRequired,
};

export default Login;
