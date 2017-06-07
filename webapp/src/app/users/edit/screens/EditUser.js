import React from 'react';
import {FormContainer} from '../../../../lib/form/FormContainer';
import FormFieldContainer from '../../../../lib/form/FormFieldContainer';
import Form from '../../../../widgets/Form';
import FormRow from '../../../../widgets/FormRow';
import FormField from '../../../../widgets/FormField';
import FormLabel from '../../../../widgets/FormLabel';
import TextInput from '../../../../widgets/TextInput';
import Button from '../../../../widgets/Button';
import styles from './EditUser.less';
import Alert from '../../../../widgets/Alert';

class EditUser extends React.Component {
    render() {
        return (
            <div>
                <h2>Modification de profil</h2>

                {
                    this.props.resetPasswordTokenSent &&
                    <Alert type="success"
                           text="Un email content le lien de réinitialisation du mot de passe a été envoyé."/>
                }
                {
                    Object.keys(this.props.errors).length > 0 &&
                    <Alert type="error"
                           text="Données invalides."/>
                }

                <FormContainer name="editUser"
                               onSubmit={(e, data) => {
                                   if (typeof data !== 'undefined') {
                                       this.props.save({
                                           firstName: data.firstName,
                                           lastName: data.lastName,
                                           email: data.email,
                                       });
                                   }
                                   e.preventDefault();
                               }}
                               initialValues={{
                                   firstName: this.props.firstName,
                                   lastName: this.props.lastName,
                                   email: this.props.email,
                               }}
                >
                    <Form>
                        <FormRow className={this.props.errors.firstName ? styles.error : ''}>
                            <FormLabel>Prénom</FormLabel>
                            <FormField>
                                <FormFieldContainer name="firstName">
                                    <TextInput placeholder="Prénom"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow className={this.props.errors.lastName ? styles.error : ''}>
                            <FormLabel>Nom</FormLabel>
                            <FormField>
                                <FormFieldContainer name="lastName">
                                    <TextInput placeholder="Nom"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow className={this.props.errors.email ? styles.error : ''}>
                            <FormLabel>Email</FormLabel>
                            <FormField>
                                <FormFieldContainer name="email">
                                    <TextInput placeholder="Email"/>
                                </FormFieldContainer>
                            </FormField>
                        </FormRow>
                        <FormRow>
                            <div className={styles.buttonsRow}>
                                <Button type="default" submit={false} enabled={!this.props.saving} onClick={(e) => {
                                    this.props.sendResetPasswordToken(this.props.personId);
                                    e.preventDefault();
                                }}>
                                    Changer mon mot de passe
                                </Button>
                                <Button type="primary" submit={true} enabled={!this.props.saving}>
                                    Sauver
                                </Button>
                            </div>
                        </FormRow>
                    </Form>
                </FormContainer>
            </div>
        );
    }
}

EditUser.propTypes = {
    save: React.PropTypes.func.isRequired,
    sendResetPasswordToken: React.PropTypes.func.isRequired,
    firstName: React.PropTypes.string.isRequired,
    lastName: React.PropTypes.string.isRequired,
    personId: React.PropTypes.string.isRequired,
    email: React.PropTypes.string.isRequired,
    saving: React.PropTypes.bool.isRequired,
    resetPasswordTokenSent: React.PropTypes.bool.isRequired,
    errors: React.PropTypes.object.isRequired,
};

export default EditUser;
