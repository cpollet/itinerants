import React from 'react';
import styles from './FormField.less';

class FormField extends React.Component {
    render() {
        return (
            <div className={styles.component}>
                {this.props.children}
            </div>
        );
    }
}

FormField.propTypes = {
    children: React.PropTypes.node.isRequired
};

export default FormField;
