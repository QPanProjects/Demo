import React, { Component } from 'react';
import { createCar } from '../util/APIUtils';
import { CAR_TEXT_MAX_LENGTH } from '../constants';
import './NewCar.css';  
import { Form, Input, Button, Icon, Select, Col, notification } from 'antd';
const Option = Select.Option;
const FormItem = Form.Item;
const { TextArea } = Input

class NewCar extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            brand: {
                text: ''
            },
            model: {
                text: ''
            },
            version: {
                text: 'v1'
            },
            yearOfRelease: {
                text: ''
            },
            price: {
                text: ''
            },
            fuelConsumption: {
                text: ''
            },
            annualMaintenanceCost: {
                text: ''
            },

            store: {
                text: ''
            }
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleChange(event) {
        const { target: { name, value } } = event
        
        if(name === 'yearOfRelease') {
            this.setState({
                [name]: {
                    text: value,
                    ...this.validateYear(value)
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
        event.preventDefault();

        const carData = {
            brand: this.state.brand.text,
            model: this.state.model.text,
            version: this.state.version.text,
            yearOfRelease: this.state.yearOfRelease.text,
            price: this.state.price.text,
            fuelConsumption: this.state.fuelConsumption.text,
            annualMaintenanceCost: this.state.annualMaintenanceCost.text,
            store: 1,
            // store: this.state.store.text,
        };

        createCar(carData)
        .then(response => {
            this.props.history.push("/");
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login create car.');    
            } else {
                notification.error({
                    message: 'Cars App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });              
            }
        });
    }

    validateText = (inputText) => {
        if(inputText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter value!'
            }
        } else if (inputText.length > CAR_TEXT_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Text is too long (Maximum ${CAR_TEXT_MAX_LENGTH} characters allowed)`
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

    isFormInvalid() {
        if(this.state.brand.validateStatus !== 'success' ||
           this.state.model.validateStatus !== 'success' ||
           this.state.yearOfRelease.validateStatus !== 'success') {
            return true;
        }
    }

    render() {
        return (
            <div className="new-car-container">
                <h1 className="page-title">Create Car</h1>
                <div className="new-car-content">
                    <Form onSubmit={this.handleSubmit} className="create-car-form">
                        <FormItem validateStatus={this.state.brand.validateStatus}
                            help={this.state.brand.errorMsg} className="car-form-row">
                            <Input 
                                name="brand"
                                placeholder = 'Brand'
                                size="large"
                                value = {this.state.brand.text}
                                onChange = {this.handleChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.model.validateStatus}
                            help={this.state.model.errorMsg} className="car-form-row">
                            <Input 
                                name="model"
                                placeholder = 'Model'
                                size="large"
                                value = {this.state.model.text}
                                onChange = {this.handleChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.yearOfRelease.validateStatus}
                            help={this.state.yearOfRelease.errorMsg} className="car-form-row">
                            <Input 
                                name="yearOfRelease"
                                placeholder = 'Year Of Release'
                                size="large"
                                value = {this.state.yearOfRelease.text}
                                onChange = {this.handleChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.price.validateStatus}
                            help={this.state.price.errorMsg} className="car-form-row">
                            <Input 
                                name="price"
                                placeholder = 'Price &euro;'
                                size="large"
                                value = {this.state.price.text}
                                onChange = {this.handleChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.fuelConsumption.validateStatus}
                            help={this.state.fuelConsumption.errorMsg} className="car-form-row">
                            <Input 
                                name="fuelConsumption"
                                placeholder = 'Fuel Consumption Km/L'
                                size="large"
                                value = {this.state.fuelConsumption.text}
                                onChange = {this.handleChange} />
                        </FormItem>
                        <FormItem validateStatus={this.state.annualMaintenanceCost.validateStatus}
                            help={this.state.annualMaintenanceCost.errorMsg} className="car-form-row">
                            <Input 
                                name="annualMaintenanceCost"
                                placeholder = 'Annual Maintenance Cost &euro;/Year'
                                size="large"
                                value = {this.state.annualMaintenanceCost.text}
                                onChange = {this.handleChange} />
                        </FormItem>
                        {/* <FormItem validateStatus={this.state.store.validateStatus}
                            help={this.state.store.errorMsg} className="car-form-row">
                            <Input 
                                name="store"
                                placeholder = 'Store'
                                size="large"
                                value = {this.state.store.text}
                                onChange = {this.handleChange} />
                        </FormItem> */}
                        <FormItem className="car-form-row">
                            <Button type="primary" 
                                htmlType="submit" 
                                size="large" 
                                disabled={this.isFormInvalid()}
                                className="create-car-form-button">Create Car</Button>
                        </FormItem>
                    </Form>
                </div>    
            </div>
        );
    }
}

export default NewCar;