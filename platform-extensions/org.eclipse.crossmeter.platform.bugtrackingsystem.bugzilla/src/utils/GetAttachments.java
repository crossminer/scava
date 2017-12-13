/*
 * Copyright 2011 Thomas Golden
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.AttachmentFactory;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * The {@code GetAttachments} class allows clients to retrieve {@link Attachment} objects from existing
 * {@link Bug Bugs} within a Bugzilla installation.
 * 
 * Note that Bugzilla 3.6 did not provide the actual attachment binary data, and thus only metadata
 * will be returned for this version.
 * 
 * @author Tom
 *
 */
public class GetAttachments implements BugzillaMethod {

	private static final String METHOD_NAME = "Bug.attachments";

	private Map<Object, Object> hash = new HashMap<Object, Object>();
	private Map<Object, Object> params = new HashMap<Object, Object>();
	private List<Integer> bugIdList = new ArrayList<Integer>();
	
	/**
	 * Retrieves the {@link Attachment Attachments} for the specified {@link Bug}.
	 * @param bug A {@code Bug} to retrieve attachments for.
	 */
	public GetAttachments(Bug bug) {
//		params.put("ids", bug.getID());
		bugIdList.add(bug.getID());
		params.put("ids", bugIdList);
	}

	/**
	 * Retrieves the {@link Attachment Attachments} for the {@link Bug} denoted by the supplied ID.
	 * @param id A unique integer ID pointing to a {@code Bug} in the Bugzilla installation.
	 */
	public GetAttachments(int id) {
//        params.put("ids", id);
		bugIdList.add(id);
		params.put("ids", bugIdList);
	}

	public GetAttachments(List<Integer> bugIdList) { // int[] idArray) {
		this.bugIdList = bugIdList;
		params.put("ids", bugIdList);
	}

	/**
	 * Returns the {@code List} of {@link Attachment Attachments} belonging to the provided {@link Bug}.
	 * @return A {@code List} of {@code Attachment} objects.
	 */
	public List<Attachment> getAttachments() {
		List<Attachment> attachments = new ArrayList<Attachment>();

		if(hash.containsKey("bugs")) {
			AttachmentFactory factory = new AttachmentFactory();

			@SuppressWarnings("unchecked")
			Map<Object, Object> attachMap = (Map<Object, Object>)hash.get("bugs");
			Collection<Object> values = attachMap.values();
			for(Object obj : values) {
				Object[] arr = (Object[])obj;
				for(Object i : arr) {
					@SuppressWarnings("unchecked")
					Map<Object, Object> attachment = (Map<Object, Object>)i;

					factory.newAttachment()
						.setID((Integer)attachment.get("id"))
						.setBugID((Integer)attachment.get("bug_id"))
						.setName((String)attachment.get("file_name"))
						.setSummary((String)attachment.get("summary"))
						.setCreator((String)attachment.get("creator"))
						.setMime((String)attachment.get("content_type"));

					if(attachment.containsKey("data")) {
						//Bugzilla 3.6 did not provide the actual attachment blob
						factory.setData((byte[])attachment.get("data"));
					}
					attachments.add(factory.createAttachment());
				}
			}
		}

		return attachments;
	}

	@Override
	public void setResultMap(Map<Object, Object> hash) {
		this.hash = hash;
	}

	@Override
	public Map<Object, Object> getParameterMap() {
		return Collections.unmodifiableMap(params);
	}

	@Override
	public String getMethodName() {
		return METHOD_NAME;
	}

}