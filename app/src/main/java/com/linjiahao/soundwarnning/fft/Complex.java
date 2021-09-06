package com.linjiahao.soundwarnning.fft;


import androidx.annotation.NonNull;

public class Complex {

    public double real, imaginary;

    public Complex(double real, double im) {
        this.real = real;
        this.imaginary = im;
    }

    public Complex() {
        this(0, 0);
    }

    public Complex(Complex c) {
        this(c.real, c.imaginary);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.real + "+" + this.imaginary + "i)";
    }

    //加法
    public final Complex add(Complex c) {
        return new Complex(this.real + c.real, this.imaginary + c.imaginary);
    }

    //减法
    public final Complex minus(Complex c) {
        return new Complex(this.real - c.real, this.imaginary - c.imaginary);
    }

    //求模值
    public final double getMod() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    //乘法
    public final Complex multiply(Complex c) {
        return new Complex(
                this.real * c.real - this.imaginary * c.imaginary,
                this.real * c.imaginary + this.imaginary * c.real);
    }
}