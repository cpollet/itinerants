import React from 'react';
import styles from './FormRow.css';

class FormRow extends React.Component {
    render() {
        return (
            <div className={styles.component}>
                {this.props.children}
            </div>
        );
    }
}

FormRow.propTypes = {
    children: React.PropTypes.node.isRequired
};

export default FormRow;
