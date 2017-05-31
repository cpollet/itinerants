import React from 'react';
import {FormContainer} from '../../../../lib/form/FormContainer';
import FormFieldContainer from '../../../../lib/form/FormFieldContainer';
import Form from '../../../../widgets/Form';
import FormRow from '../../../../widgets/FormRow';
import FormField from '../../../../widgets/FormField';
import FormLabel from '../../../../widgets/FormLabel';
import TextInput from '../../../../widgets/TextInput';
import styles from './CreateUser.less';
import Button from '../../../../widgets/Button';
import MultiSelect from '../../../../widgets/MultiSelect';
import Alert from '../../../../widgets/Alert';

class CreateUser extends React.Component {
    render() {
        return (
            <div>
                <h2>Création d'un utilisateur</h2>

                {this.props.createUserError && <Alert type="error">Une erreur est survenue</Alert>}

                <FormContainer name="createUser"
                               onSubmit={(e, data) => {
                                   if (typeof data !== 'undefined') {
                                       this.props.create({
                                           username: data.username,
                                           firstName: data.firstName,
                                           lastName: data.lastName,
                                           email: data.email,
                                           targetRatio: data.targetRatio,
                                           roles: data.roles,
                                       });
                                   }
                                   e.preventDefault();
                               }}
                               initialValues={{
                                   username: '',
                                   firstName: '',
                                   lastName: '',
                                   email: '',
                                   targetRatio: 0,
                                   roles: ['user'],
                               }}
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
                            <FormLabel>Prénom</FormLabel>
                            <FormField>
                                <FormFieldContainer name="firstName">
                                    <TextInput placeholder="Prénom"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel>Nom</FormLabel>
                            <FormField>
                                <FormFieldContainer name="lastName">
                                    <TextInput placeholder="Nom"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel>Email</FormLabel>
                            <FormField>
                                <FormFieldContainer name="email">
                                    <TextInput placeholder="Email"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel>Ratio</FormLabel>
                            <FormField>
                                <FormFieldContainer name="targetRatio">
                                    <TextInput placeholder="Ratio"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <FormLabel>Rôles</FormLabel>
                            <FormField>
                                <FormFieldContainer name="roles">
                                    <MultiSelect>
                                        <option value="admin">Administrateur</option>
                                        <option value="user">Utilisateur</option>
                                    </MultiSelect>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <div className={styles.buttonsRow}>
                                <Button type="primary" submit={true}>Créer</Button>
                            </div>
                        </FormRow>
                    </Form>
                </FormContainer>
            </div>
        );
    }
}

CreateUser.propTypes = {
    create: React.PropTypes.func.isRequired,
    createUserError: React.PropTypes.bool,
};

export default CreateUser;
