package com.burnmycalories.aop.login.aspect;

import android.content.Context;
import android.util.Log;

import com.burnmycalories.aop.login.core.ILogin;
import com.burnmycalories.aop.login.core.LoginSDK;
import com.burnmycalories.aop.login.exception.AnnotationException;
import com.burnmycalories.aop.login.exception.NoInitException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class LoginInterceptAspect {

    private static final String TAG = "LoginInterceptAspect";

    @Pointcut("execution(@com.fomin.aop.login.aspect.LoginIntercept private * *..*.*(..))")//这里使用private是因为方法中全部是private，也可以去除
    public void loginFilter() {
    }

    @SuppressWarnings("CheckStyle")
    @Around("loginFilter()")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        ILogin login = LoginSDK.getInstance().getLogin();
        if (login == null) {
            throw new NoInitException("LoginSDK not initialised");
        }

        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new AnnotationException("LoginIntercept annotation not correct");
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Log.d("Aspect", String.valueOf(methodSignature.getName()));
        Log.d("Aspect", String.valueOf(methodSignature.getMethod() == null));
        LoginIntercept loginIntercept = methodSignature.getMethod().getAnnotation(LoginIntercept.class);
        if (loginIntercept== null) {
            return;
        }
        Context param = LoginSDK.getInstance().getContext();
        if (login.isLogin(param)) {
            joinPoint.proceed();
        } else {
            login.login(param, loginIntercept.actionDefine());
        }
    }
}