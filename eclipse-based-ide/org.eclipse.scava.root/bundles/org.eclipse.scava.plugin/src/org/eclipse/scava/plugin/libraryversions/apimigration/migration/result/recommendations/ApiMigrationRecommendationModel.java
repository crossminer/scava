/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations;

import java.util.List;
import java.util.Map;

import org.eclipse.scava.plugin.async.api.ApiAsyncBuilder;
import org.eclipse.scava.plugin.async.api.IApiAsyncBuilder;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationParameters;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultModel;
import org.eclipse.scava.plugin.preferences.Preferences;

import io.swagger.client.api.ApiMigrationRestControllerApi;
import io.swagger.client.model.Detection;

public class ApiMigrationRecommendationModel extends ApiMigrationResultModel {

	public ApiMigrationRecommendationModel(KnowledgeBaseAccess knowledgeBaseAccess,
			ApiMigrationParameters apiMigrationParameters) {
		super(knowledgeBaseAccess, apiMigrationParameters);
	}

	public IApiAsyncBuilder<List<Detection>> requestDetectionsAsync() {
//		if (getApiMigrationParameters().getProject().getName().toLowerCase().contains("dummy")) {
//			String json = "{}";
//			if (getApiMigrationParameters().getOldLibraryVersion().getVersion().equals("17.0")
//					&& getApiMigrationParameters().getNewLibraryVersion().getVersion().equals("18.0")) {
//				json = "[{\"clientLocation\":\"|java+method:///Main$TesterClass1/sameThreadMethod(boolean,byte,char,short,int,long,float,double,java.lang.Integer,java.lang.Double,char%5B%5D)|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/MoreExecutors/sameThreadExecutor()|\",\"newLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/MoreExecutors/sameThreadExecutor()|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/IWantToTestTheMultiSets()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/collect/Multisets/removeOccurrences(com.google.common.collect.Multiset,com.google.common.collect.Multiset)|\",\"newLibraryLocation\":\"\",\"type\":\"REMOVED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass$SomeConverter/andThen(com.google.common.base.Converter)|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/base/Converter/andThen(com.google.common.base.Converter)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/base/Converter/andThen(com.google.common.base.Converter)|\",\"type\":\"FINAL_MODIFIER\",\"score\":1},{\"clientLocation\":\"|java+method:///first/third/MegaTestClass/objectTesterMethod()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/base/Objects/toStringHelper(java.lang.Object)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/base/Objects/toStringHelper(java.lang.Object)|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/IWantToTestTheMultiSets()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/collect/Multisets/removeOccurrences(com.google.common.collect.Multiset,com.google.common.collect.Multiset)|\",\"newLibraryLocation\":\"\",\"type\":\"PARAMS_LIST\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/byteSinkMethod()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/weTestHereTheByteSource()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSource|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSource|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/byteSinkMethod()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/io/ByteStreams/asByteSink(com.google.common.io.OutputSupplier)|\",\"newLibraryLocation\":\"\",\"type\":\"REMOVED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/methodToTestIterator()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/collect/Iterators/emptyIterator()|\",\"newLibraryLocation\":\"|java+method:///com/google/common/collect/Iterators/emptyIterator()|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/third/MegaTestClass/objectTesterMethod()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/base/Objects/firstNonNull(java.lang.Object,java.lang.Object)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/base/Objects/firstNonNull(java.lang.Object,java.lang.Object)|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+class:///Main$1|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+constructor:///Main$1/Main$1(Main)|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/weTestHereTheByteSource()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/io/ByteSource/getInput()|\",\"newLibraryLocation\":\"\",\"type\":\"REMOVED\",\"score\":1}]";
//			} else if (getApiMigrationParameters().getOldLibraryVersion().getVersion().equals("17.0")
//					&& getApiMigrationParameters().getNewLibraryVersion().getVersion().equals("19.0")) {
//				json = "[{\"clientLocation\":\"|java+method:///Main$TesterClass1/sameThreadMethod(boolean,byte,char,short,int,long,float,double,java.lang.Integer,java.lang.Double,char%5B%5D)|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/MoreExecutors/sameThreadExecutor()|\",\"newLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/MoreExecutors/sameThreadExecutor()|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+class:///first/second/BigTestClass$1|\",\"oldLibraryLocation\":\"|java+interface:///com/google/common/util/concurrent/FutureFallback|\",\"newLibraryLocation\":\"|java+interface:///com/google/common/util/concurrent/FutureFallback|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass$SomeConverter/andThen(com.google.common.base.Converter)|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/base/Converter/andThen(com.google.common.base.Converter)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/base/Converter/andThen(com.google.common.base.Converter)|\",\"type\":\"FINAL_MODIFIER\",\"score\":1},{\"clientLocation\":\"|java+method:///first/third/MegaTestClass/objectTesterMethod()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/base/Objects/toStringHelper(java.lang.Object)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/base/Objects/toStringHelper(java.lang.Object)|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/byteSinkMethod()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/weTestHereTheByteSource()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSource|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSource|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/anotherMethodForFutures()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/Futures/withFallback(com.google.common.util.concurrent.ListenableFuture,com.google.common.util.concurrent.FutureFallback)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/Futures/withFallback(com.google.common.util.concurrent.ListenableFuture,com.google.common.util.concurrent.FutureFallback)|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/anotherMethodForFutures()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/byteSinkMethod()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/io/ByteStreams/asByteSink(com.google.common.io.OutputSupplier)|\",\"newLibraryLocation\":\"\",\"type\":\"REMOVED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/methodToTestIterator()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/collect/Iterators/emptyIterator()|\",\"newLibraryLocation\":\"|java+method:///com/google/common/collect/Iterators/emptyIterator()|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/third/MegaTestClass/objectTesterMethod()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/base/Objects/firstNonNull(java.lang.Object,java.lang.Object)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/base/Objects/firstNonNull(java.lang.Object,java.lang.Object)|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+class:///Main$1|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+constructor:///Main$1/Main$1(Main)|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"newLibraryLocation\":\"|java+class:///com/google/common/io/ByteSink|\",\"type\":\"IMPLEMENTS\",\"score\":1},{\"clientLocation\":\"|java+method:///Main/weTestHereTheByteSource()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/io/ByteSource/getInput()|\",\"newLibraryLocation\":\"\",\"type\":\"REMOVED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/futuresTest()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/findSomeListenableIntegerFuture()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/findSomeListenableIntegerFuture()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/futuresTest()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"type\":\"EXTENDS\",\"score\":1}]";
//			} else if (getApiMigrationParameters().getOldLibraryVersion().getVersion().equals("18.0")
//					&& getApiMigrationParameters().getNewLibraryVersion().getVersion().equals("19.0")) {
//				json = "[{\"clientLocation\":\"|java+class:///first/second/BigTestClass$1|\",\"oldLibraryLocation\":\"|java+interface:///com/google/common/util/concurrent/FutureFallback|\",\"newLibraryLocation\":\"|java+interface:///com/google/common/util/concurrent/FutureFallback|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/anotherMethodForFutures()|\",\"oldLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/Futures/withFallback(com.google.common.util.concurrent.ListenableFuture,com.google.common.util.concurrent.FutureFallback)|\",\"newLibraryLocation\":\"|java+method:///com/google/common/util/concurrent/Futures/withFallback(com.google.common.util.concurrent.ListenableFuture,com.google.common.util.concurrent.FutureFallback)|\",\"type\":\"DEPRECATED\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/anotherMethodForFutures()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/futuresTest()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/findSomeListenableIntegerFuture()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/second/BigTestClass/findSomeListenableIntegerFuture()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/Futures|\",\"type\":\"EXTENDS\",\"score\":1},{\"clientLocation\":\"|java+method:///first/HugeTestClass/futuresTest()|\",\"oldLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"newLibraryLocation\":\"|java+class:///com/google/common/util/concurrent/SettableFuture|\",\"type\":\"EXTENDS\",\"score\":1}]";
//			}
//			Type resultType = new TypeToken<List<Detection>>() {
//			}.getType();
//
//			return DummyApiAsyncBuilder.buildToSuccess(knowledgeBaseAccess.getApiMigrationRestController()
//					.getApiClient().getJSON().deserialize(json, resultType));
//		}

		return ApiAsyncBuilder.build(apiCallback -> {
			ApiMigrationRestControllerApi apiMigrationRestController = knowledgeBaseAccess
					.getApiMigrationRestController(Preferences.TIMEOUT_APIMIGRATION_RECOMMENDATIONS);

			return apiMigrationRestController.getDetectionUsingPOSTAsync(getApiMigrationParameters().getM3Model(),
					getApiMigrationParameters().getOldLibraryVersion().toMavenCoord(),
					getApiMigrationParameters().getNewLibraryVersion().toMavenCoord(), apiCallback);
		});
	}

	public IApiAsyncBuilder<Map<String, List<String>>> requestSnippetsAsync() {
//		if (getApiMigrationParameters().getProject().getName().toLowerCase().contains("dummy")) {
//			String json = "{}";
//			if (getApiMigrationParameters().getOldLibraryVersion().getVersion().equals("17.0")
//					&& getApiMigrationParameters().getNewLibraryVersion().getVersion().equals("18.0")) {
//				json = "{\"|java+method:///first/third/MegaTestClass/objectTesterMethod()|\":[\"@Override\\n  public String toString() {\\n    return MoreObjects.toStringHelper(this)\\n        .add(\\\"ipAddress\\\", ipAddress)\\n        .add(\\\"ipPrefixLen\\\", ipPrefixLen)\\n        .add(\\\"gateway\\\", gateway)\\n        .add(\\\"bridge\\\", bridge)\\n        .add(\\\"portMapping\\\", portMapping)\\n        .add(\\\"ports\\\", ports)\\n        .add(\\\"macAddress\\\", macAddress)\\n        .toString();\\n  }\",\"@Override\\n  public String toString() {\\n    return MoreObjects.toStringHelper(this)\\n        .add(\\\"job\\\", job)\\n        .add(\\\"taskStatuses\\\", taskStatuses)\\n        .add(\\\"deployments\\\", deployments)\\n        .toString();\\n  }\"]}";
//			} else if (getApiMigrationParameters().getOldLibraryVersion().getVersion().equals("17.0")
//					&& getApiMigrationParameters().getNewLibraryVersion().getVersion().equals("19.0")) {
//				json = "{}";
//			} else if (getApiMigrationParameters().getOldLibraryVersion().getVersion().equals("18.0")
//					&& getApiMigrationParameters().getNewLibraryVersion().getVersion().equals("19.0")) {
//				json = "{\"|java+method:///first/second/BigTestClass/anotherMethodForFutures()|\":[\"@Override\\n    public <T> Future<T> submit(Callable<T> tCallable)\\n    {\\n        requireNonNull(tCallable, \\\"Task object is null\\\");\\n        try {\\n            return Futures.immediateFuture(tCallable.call());\\n        }\\n        catch (Exception e) {\\n            return Futures.immediateFailedFuture(e);\\n        }\\n    }\"],\"|java+method:///first/HugeTestClass/futuresTest()|\":[\"@Override\\n    public <T> Future<T> submit(Callable<T> tCallable)\\n    {\\n        requireNonNull(tCallable, \\\"Task object is null\\\");\\n        try {\\n            return Futures.immediateFuture(tCallable.call());\\n        }\\n        catch (Exception e) {\\n            return Futures.immediateFailedFuture(e);\\n        }\\n    }\"],\"|java+method:///first/second/BigTestClass/findSomeListenableIntegerFuture()|\":[\"@Override\\n    public <T> Future<T> submit(Callable<T> tCallable)\\n    {\\n        requireNonNull(tCallable, \\\"Task object is null\\\");\\n        try {\\n            return Futures.immediateFuture(tCallable.call());\\n        }\\n        catch (Exception e) {\\n            return Futures.immediateFailedFuture(e);\\n        }\\n    }\"]}";
//			}
//			Type resultType = new TypeToken<Map<String, List<String>>>() {
//			}.getType();
//
//			return DummyApiAsyncBuilder.buildToSuccess(knowledgeBaseAccess.getApiMigrationRestController().getApiClient()
//					.getJSON().deserialize(json, resultType));
//		}

		return ApiAsyncBuilder.build(apiCallback -> {
			ApiMigrationRestControllerApi apiMigrationRestController = knowledgeBaseAccess
					.getApiMigrationRestController(Preferences.TIMEOUT_APIMIGRATION_RECOMMENDATIONS);

			return apiMigrationRestController.getRecommendationSnippetUsingPOSTAsync(
					getApiMigrationParameters().getM3Model(),
					getApiMigrationParameters().getOldLibraryVersion().toMavenCoord(),
					getApiMigrationParameters().getNewLibraryVersion().toMavenCoord(), apiCallback);
		});

	}
}
