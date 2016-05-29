/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;
import java.io.Serializable;
/**
 *
 * @author wirox91
 */

public interface IDistance <T> extends IDivergence<T>, Serializable {

    @Override
    double Compute(T u, T v);
}
