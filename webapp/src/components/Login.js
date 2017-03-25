import React from 'react';
import Form from './core/Form';
import FormRow from './core/FormRow';
import FormLabel from './core/FormLabel';
import FormField from './core/FormField';
import TextInput from './core/TextInput';
import PasswordInput from './core/PasswordInput';
import Button from './core/Button';
import {FormContainer} from '../lib/form/FormContainer';
import FormFieldContainer from '../lib/form/FormFieldContainer';
import Alert from './core/Alert';

class Login extends React.Component {
    render() {
        if (this.props.invalidCredentials) {
            var message = (
                <Alert  type="error" text="Nom d'utilisateur ou mot de passe invalide."/>
            );
        }

        return (
            <div>
                {message}
                <FormContainer name="login" onSubmit={(e, data) => {
                    if (typeof data !== 'undefined') {
                        this.props.login(data.username, data.password);
                    }
                    e.preventDefault();
                }}>
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
                            <FormLabel>&nbsp;</FormLabel>
                            <FormField>{/* TODO create a FormButtons component? */}
                                <Button type="primary" submit={true}>Connexion</Button>
                            </FormField>
                        </FormRow>
                    </Form>
                </FormContainer>
            </div>
        );
    }
}

Login.propTypes = {
    login: React.PropTypes.func.isRequired,
    invalidCredentials: React.PropTypes.bool
};

export default Login;
