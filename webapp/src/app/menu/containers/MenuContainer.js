import {connect} from 'react-redux';
import Menu from '../screens/Menu';

function mapStateToProps(state, ownProps) {
    return {
        active: function (path) {
            switch (path) {
                case '/logout':
                    return '';
                case '/login':
                    return 'login';
                case '/':
                case '/future':
                    return 'future';
                case '/plan':
                    return 'plan';
                case '/profile':
                    return 'profile';
                case '/admin':
                case '/admin/users/create':
                    return 'admin';
                case '/about':
                    return 'about';
            }
        }(ownProps.location.pathname),
        isAdmin: state.app.auth.roles.indexOf('admin') > -1,
        isAuthenticated: state.app.auth.token !== null
    };
}

export default connect(mapStateToProps, null)(Menu);
