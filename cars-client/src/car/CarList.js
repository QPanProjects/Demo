import React, { Component } from 'react';
import { getAllCars, getAllUserCreatedCars, getAllCarsBySearch, getAllUserCreatedCarsBySearch } from '../util/APIUtils';
import Car from './Car';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Form, Input, Button, Icon, notification } from 'antd';
import { CAR_LIST_SIZE, SEARCH_LIST_SIZE, SEARCH_TEXT_MAX_LENGTH } from '../constants';
import { withRouter } from 'react-router-dom';
import './CarList.css';

const { Search } = Input;
const FormItem = Form.Item;

class CarList extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            search: { 
                text: "" 
            },
            cars: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            isLoading: false
        };
        this.loadCarList = this.loadCarList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleReset = this.handleReset.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    loadCarList(page = 0, size = CAR_LIST_SIZE) {
        let promise;
        if(this.props.username) {
            if(this.props.type === 'USER_CREATED_CARS') {
                promise = getAllUserCreatedCars(this.props.username, page, size);
            }
        } else {
            promise = getAllCars(page, size);
        }

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
        .then(response => {
            // const cars = this.state.cars.slice();
            const cars = [];
            
            if (this._isMounted) {
                this.setState({
                    cars: cars.concat(response.content),
                    page: response.page,
                    size: response.size,
                    totalElements: response.totalElements,
                    totalPages: response.totalPages,
                    last: response.last,
                    isLoading: false
                })
            }
        }).catch(error => {
            if (this._isMounted) {
                this.setState({
                    isLoading: false
                })
            }
        });
        
    }

    handleLoadMore() {
        this.loadCarList(this.state.page + 1);
    }

    handleChange(event) {
        const { target: { name, value } } = event
        
        if(name === "search") {
            this.setState({
                search: {
                    text: value,
                    ...this.validateText(value)
                }
            });
        } else {
            this.setState({
                [name]: {
                    text: value,
                    ...this.validateText(value)
                }
            });
        }
    }

    handleSubmit(event) {
        // event.preventDefault();

        let promise;
        if(this.props.username) {
            if(this.props.type === 'USER_CREATED_CARS') {
                // promise = getAllUserCreatedCarsBySearch(this.props.username, this.state.search.text, 0, SEARCH_LIST_SIZE);
                if(this.state.search.text.length === 0) {
                    promise = getAllUserCreatedCars(this.props.username, 0, SEARCH_LIST_SIZE);
                } else {
                    promise = getAllUserCreatedCarsBySearch(this.props.username, this.state.search.text, 0, SEARCH_LIST_SIZE);
                }
            }
        } else {
            // promise = getAllCarsBySearch(this.state.search.text, 0, SEARCH_LIST_SIZE);
            if(this.state.search.text.length === 0) {
                promise = getAllCars(this.props.username, 0, SEARCH_LIST_SIZE);
            } else {
                promise = getAllCarsBySearch(this.state.search.text, 0, SEARCH_LIST_SIZE);
            }
        }

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
        .then(response => {
            const cars = [];

            this.setState({
                search: {
                    text: this.state.search.text
                },
                cars: cars.concat(response.content),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    handleReset(event) {
        // event.preventDefault();

        this.loadCarList();
    }

    isFormInvalid() {
        if(this.state.search.validateStatus !== 'success') {
            return true;
        }
    }

    validateText = (inputText) => {
        if(inputText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter value!'
            }
        } else if (inputText.length > SEARCH_TEXT_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Text is too long (Maximum ${SEARCH_TEXT_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    validateYear = (inputText) => {
        if(inputText.length == 4) {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }  else {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter value!'
            }
        }
    }

    componentDidMount() {
        this._isMounted = true;

        this.loadCarList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                search: { text: "" },
                cars: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                isLoading: false
            });    
            this.loadCarList();
        }
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    render() {
        const carViews = [];
        this.state.cars.forEach((car, carIndex) => {
            carViews.push(<Car 
                key={car.id} 
                car={car}
                username={this.props.username} />)
        });

        return (
            <div className="cars-container">
                <Search 
                    name="search"
                    placeholder = 'Brand, Model, Year'
                    value = {this.state.search.text}
                    onChange = {this.handleChange}
                    onPressEnter = {this.handleSubmit}
                    onSearch = {this.handleSubmit}
                    loading enterButton />
                <br />

                {carViews}
                {
                    !this.state.isLoading && this.state.cars.length === 0 ? (
                        <div className="no-cars-found">
                            <span>No Cars Found.</span>
                        </div>    
                    ): null
                }  
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-cars"> 
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                                <Icon type="plus" /> Load more
                            </Button>
                        </div>): null
                }              
                {
                    this.state.isLoading ? 
                    <LoadingIndicator />: null
                }
            </div>
        );
    }
}

export default withRouter(CarList);