/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package com.pmitkowski.firebase.dynamiclinks;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;

import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

@Kroll.module(name="FirebaseDeepLinking", id="com.pmitkowski.firebase.dynamiclinks")
public class FirebaseDeepLinkingModule extends KrollModule
{

	// Standard Debugging variables
	private static final String LCAT = "FirebaseDeepLinkingModule";
	private static FirebaseDeepLinkingModule instance = null;

	public FirebaseDeepLinkingModule() {
		super();
		instance = this;
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		// put module init code that needs to run when the application is created
	}

	@Kroll.method
	public void handleDeepLink() {
		Activity currentActivity = TiApplication.getAppRootOrCurrentActivity();
		Intent intent = currentActivity.getIntent();
		if (intent == null) {
			return;
		}
		Log.w(LCAT, "Links instance not null: " + (FirebaseDynamicLinks.getInstance() != null));
		Log.w(LCAT, "current activity not null: " + (currentActivity != null));
		FirebaseDynamicLinks.getInstance()
        .getDynamicLink(intent)
        .addOnSuccessListener(currentActivity, new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                Uri deepLink = null;
                if (pendingDynamicLinkData != null) {
					deepLink = pendingDynamicLinkData.getLink();

					KrollDict queryParams = new KrollDict();
					Set<String> queryParamNames = deepLink.getQueryParameterNames();
					for (String paramName : queryParamNames) {
						queryParams.put(paramName, deepLink.getQueryParameter(paramName));
					}
					fireEvent("deeplink:new", queryParams);
                }
            }
        })
        .addOnFailureListener(currentActivity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
				fireEvent("deeplink:error", e.getMessage());
            }
        });	
	}
}
