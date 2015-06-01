/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 NBCO Yandex.Money LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ru.yandex.money.android.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import com.yandex.money.api.methods.BaseProcessPayment;
import com.yandex.money.api.model.Error;

import java.util.Map;

import ru.yandex.money.android.utils.Parcelables;

/**
 * @author Slava Yasevich (vyasevich@yamoney.ru)
 */
public abstract class BaseProcessPaymentParcelable implements Parcelable {

    public final BaseProcessPayment baseProcessPayment;

    public BaseProcessPaymentParcelable(BaseProcessPayment baseProcessPayment) {
        if (baseProcessPayment == null) {
            throw new NullPointerException("baseProcessPayment is null");
        }
        this.baseProcessPayment = baseProcessPayment;
    }

    protected BaseProcessPaymentParcelable(Parcel parcel) {
        baseProcessPayment = createBaseProcessPayment(parcel,
                (BaseProcessPayment.Status) parcel.readSerializable(),
                (Error) parcel.readSerializable(), parcel.readString(), parcel.readString(),
                Parcelables.readStringMap(parcel), parcel.readLong());
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(baseProcessPayment.status);
        dest.writeSerializable(baseProcessPayment.error);
        dest.writeString(baseProcessPayment.invoiceId);
        dest.writeString(baseProcessPayment.acsUri);
        Parcelables.writeStringMap(dest, baseProcessPayment.acsParams);
        Parcelables.writeNullableLong(dest, baseProcessPayment.nextRetry);
    }

    protected abstract BaseProcessPayment createBaseProcessPayment(
            Parcel parcel, BaseProcessPayment.Status status, Error error, String invoiceId,
            String acsUri, Map<String, String> acsParams, Long nextRetry);
}