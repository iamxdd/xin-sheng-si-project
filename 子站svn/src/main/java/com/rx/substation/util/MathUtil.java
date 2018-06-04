package com.rx.substation.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MathUtil {  
    
    public static BigDecimal getBigDecimal( Object value ) {  
        BigDecimal ret = null;  
        if( value != null ) {  
            if( value instanceof BigDecimal ) {  
                ret = (BigDecimal) value;  
            } else if( value instanceof String ) {  
                ret = new BigDecimal( (String) value );  
            } else if( value instanceof BigInteger ) {  
                ret = new BigDecimal( (BigInteger) value );  
            } else if( value instanceof Number ) {  
                ret = new BigDecimal( ((Number)value).doubleValue() );  
            } else {  
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");  
            }  
        }  
        return ret;  
    }


    public   static   double   round(double   v,int   scale){
        if(scale<0){
            throw   new   IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal   b   =   new   BigDecimal(Double.toString(v));
        BigDecimal   one   =   new   BigDecimal("1");
        return   b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }



}  
