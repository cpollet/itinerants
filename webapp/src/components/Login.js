import React from 'react';
import Form from './core/Form';
import FormRow from './core/FormRow';
import FormLabel from './core/FormLabel';
import FormField from './core/FormField';
import TextInput from './core/TextInput';
import PasswordInput from './core/PasswordInput';
import Button from './core/Button';

class Login extends React.Component {
    render() {
        return (
            <Form onSubmit={(e) => {
                console.log('submit');
                e.preventDefault();
            }}>
                <FormRow>
                    <FormLabel>Nom d'utilisateur</FormLabel>
                    <FormField>
                        <TextInput placeholder="Nom d'utilisateur"/>
                    </FormField>
                </FormRow>
                <FormRow>
                    <FormLabel>Mot de passe</FormLabel>
                    <FormField>
                        <PasswordInput placeholder="Mot de passe"/>
                    </FormField>
                </FormRow>
                <FormRow>
                    <FormLabel>&nbsp;</FormLabel>
                    <FormField>{/* TODO create a FormButtons component? */}
                        <Button type="primary" submit={true}>Connexion</Button>
                    </FormField>
                </FormRow>
            </Form>
        );
    }
}

export default Login;
