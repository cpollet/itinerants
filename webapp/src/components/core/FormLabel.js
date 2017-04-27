import React from 'react';
import styles from './FormLabel.less';

class FormLabel extends React.Component {
    render() {
        return (
            <label className={styles.component}>
                {this.props.children}
            </label>
        );
    }
}

FormLabel.propTypes = {
    children: React.PropTypes.node.isRequired
};

export default FormLabel;
