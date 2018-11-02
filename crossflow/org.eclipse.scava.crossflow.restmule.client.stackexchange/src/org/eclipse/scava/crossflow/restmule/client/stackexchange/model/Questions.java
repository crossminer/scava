package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Questions {

	public Questions(){}

	@JsonProperty("comment_count") 
	private Integer commentCount;
	
	@JsonProperty("closed_date") 
	private Integer closedDate;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("locked_date") 
	private Integer lockedDate;
	
	@JsonProperty("can_flag") 
	private Boolean canFlag;
	
	@JsonProperty("answer_count") 
	private Integer answerCount;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("community_owned_date") 
	private Integer communityOwnedDate;
	
	@JsonProperty("score") 
	private Integer score;
	
	@JsonProperty("accepted_answer_id") 
	private Integer acceptedAnswerId;
	
	@JsonProperty("downvoted") 
	private Boolean downvoted;
	
	@JsonProperty("down_vote_count") 
	private Integer downVoteCount;
	
	@JsonProperty("reopen_vote_count") 
	private Integer reopenVoteCount;
	
	@JsonProperty("bounty_closes_date") 
	private Integer bountyClosesDate;
	
	@JsonProperty("closed_reason") 
	private String closedReason;
	
	@JsonProperty("favorite_count") 
	private Integer favoriteCount;
	
	@JsonProperty("favorited") 
	private Boolean favorited;
	
	@JsonProperty("last_activity_date") 
	private Integer lastActivityDate;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("question_id") 
	private Integer questionId;
	
	@JsonProperty("share_link") 
	private String shareLink;
	
	@JsonProperty("can_close") 
	private Boolean canClose;
	
	@JsonProperty("bounty_amount") 
	private Integer bountyAmount;
	
	@JsonProperty("body_markdown") 
	private String bodyMarkdown;
	
	@JsonProperty("upvoted") 
	private Boolean upvoted;
	
	@JsonProperty("protected_date") 
	private Integer protectedDate;
	
	@JsonProperty("is_answered") 
	private Boolean isAnswered;
	
	@JsonProperty("close_vote_count") 
	private Integer closeVoteCount;
	
	@JsonProperty("delete_vote_count") 
	private Integer deleteVoteCount;
	
	@JsonProperty("up_vote_count") 
	private Integer upVoteCount;
	
	@JsonProperty("view_count") 
	private Integer viewCount;
	
	@JsonProperty("last_edit_date") 
	private Integer lastEditDate;
	
	@JsonProperty("closed_details") 
	private ClosedDetails closedDetails;
	
	@JsonProperty("migrated_to") 
	private MigratedTo migratedTo;
	
	@JsonProperty("notice") 
	private Notice notice;
	
	@JsonProperty("owner") 
	private Owner owner;
	
	@JsonProperty("last_editor") 
	private LastEditor lastEditor;
	
	@JsonProperty("migrated_from") 
	private MigratedFrom migratedFrom;
	
	@JsonProperty("bounty_user") 
	private BountyUser bountyUser;
	
	@JsonProperty("answers") 
	private List<Object> answers = new ArrayList<Object>();
	
	@JsonProperty("comments") 
	private List<Object> comments = new ArrayList<Object>();
	
	@JsonProperty("tags") 
	private List<Object> tags = new ArrayList<Object>();
	
	public Integer getCommentCount() {
		return this.commentCount;
	}
	
	public Integer getClosedDate() {
		return this.closedDate;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Integer getLockedDate() {
		return this.lockedDate;
	}
	
	public Boolean getCanFlag() {
		return this.canFlag;
	}
	
	public Integer getAnswerCount() {
		return this.answerCount;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Integer getCommunityOwnedDate() {
		return this.communityOwnedDate;
	}
	
	public Integer getScore() {
		return this.score;
	}
	
	public Integer getAcceptedAnswerId() {
		return this.acceptedAnswerId;
	}
	
	public Boolean getDownvoted() {
		return this.downvoted;
	}
	
	public Integer getDownVoteCount() {
		return this.downVoteCount;
	}
	
	public Integer getReopenVoteCount() {
		return this.reopenVoteCount;
	}
	
	public Integer getBountyClosesDate() {
		return this.bountyClosesDate;
	}
	
	public String getClosedReason() {
		return this.closedReason;
	}
	
	public Integer getFavoriteCount() {
		return this.favoriteCount;
	}
	
	public Boolean getFavorited() {
		return this.favorited;
	}
	
	public Integer getLastActivityDate() {
		return this.lastActivityDate;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public Integer getQuestionId() {
		return this.questionId;
	}
	
	public String getShareLink() {
		return this.shareLink;
	}
	
	public Boolean getCanClose() {
		return this.canClose;
	}
	
	public Integer getBountyAmount() {
		return this.bountyAmount;
	}
	
	public String getBodyMarkdown() {
		return this.bodyMarkdown;
	}
	
	public Boolean getUpvoted() {
		return this.upvoted;
	}
	
	public Integer getProtectedDate() {
		return this.protectedDate;
	}
	
	public Boolean getIsAnswered() {
		return this.isAnswered;
	}
	
	public Integer getCloseVoteCount() {
		return this.closeVoteCount;
	}
	
	public Integer getDeleteVoteCount() {
		return this.deleteVoteCount;
	}
	
	public Integer getUpVoteCount() {
		return this.upVoteCount;
	}
	
	public Integer getViewCount() {
		return this.viewCount;
	}
	
	public Integer getLastEditDate() {
		return this.lastEditDate;
	}
	
	public ClosedDetails getClosedDetails() {
		return this.closedDetails;
	}
	
	public MigratedTo getMigratedTo() {
		return this.migratedTo;
	}
	
	public Notice getNotice() {
		return this.notice;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public LastEditor getLastEditor() {
		return this.lastEditor;
	}
	
	public MigratedFrom getMigratedFrom() {
		return this.migratedFrom;
	}
	
	public BountyUser getBountyUser() {
		return this.bountyUser;
	}
	
	public List<Object> getAnswers() {
		return this.answers;
	}
	
	public List<Object> getComments() {
		return this.comments;
	}
	
	public List<Object> getTags() {
		return this.tags;
	}
	
	@Override
	public String toString() {
		return "Questions [ "
			+ "commentCount = " + this.commentCount + ", "
			+ "closedDate = " + this.closedDate + ", "
			+ "link = " + this.link + ", "
			+ "lockedDate = " + this.lockedDate + ", "
			+ "canFlag = " + this.canFlag + ", "
			+ "answerCount = " + this.answerCount + ", "
			+ "body = " + this.body + ", "
			+ "title = " + this.title + ", "
			+ "communityOwnedDate = " + this.communityOwnedDate + ", "
			+ "score = " + this.score + ", "
			+ "acceptedAnswerId = " + this.acceptedAnswerId + ", "
			+ "downvoted = " + this.downvoted + ", "
			+ "downVoteCount = " + this.downVoteCount + ", "
			+ "reopenVoteCount = " + this.reopenVoteCount + ", "
			+ "bountyClosesDate = " + this.bountyClosesDate + ", "
			+ "closedReason = " + this.closedReason + ", "
			+ "favoriteCount = " + this.favoriteCount + ", "
			+ "favorited = " + this.favorited + ", "
			+ "lastActivityDate = " + this.lastActivityDate + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "questionId = " + this.questionId + ", "
			+ "shareLink = " + this.shareLink + ", "
			+ "canClose = " + this.canClose + ", "
			+ "bountyAmount = " + this.bountyAmount + ", "
			+ "bodyMarkdown = " + this.bodyMarkdown + ", "
			+ "upvoted = " + this.upvoted + ", "
			+ "protectedDate = " + this.protectedDate + ", "
			+ "isAnswered = " + this.isAnswered + ", "
			+ "closeVoteCount = " + this.closeVoteCount + ", "
			+ "deleteVoteCount = " + this.deleteVoteCount + ", "
			+ "upVoteCount = " + this.upVoteCount + ", "
			+ "viewCount = " + this.viewCount + ", "
			+ "lastEditDate = " + this.lastEditDate + ", "
			+ "closedDetails = " + this.closedDetails + ", "
			+ "migratedTo = " + this.migratedTo + ", "
			+ "notice = " + this.notice + ", "
			+ "owner = " + this.owner + ", "
			+ "lastEditor = " + this.lastEditor + ", "
			+ "migratedFrom = " + this.migratedFrom + ", "
			+ "bountyUser = " + this.bountyUser + ", "
			+ "answers = " + this.answers + ", "
			+ "comments = " + this.comments + ", "
			+ "tags = " + this.tags + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ClosedDetails {
	
		public ClosedDetails(){}
	
		@JsonProperty("reason") 
		private String reason;
		
		@JsonProperty("description") 
		private String description;
		
		@JsonProperty("on_hold") 
		private Boolean onHold;
		
		@JsonProperty("by_users") 
		private List<Object> byUsers = new ArrayList<Object>();
		
		@JsonProperty("original_questions") 
		private List<Object> originalQuestions = new ArrayList<Object>();
		
		public String getReason() {
			return this.reason;
		}
		
		public String getDescription() {
			return this.description;
		}
		
		public Boolean getOnHold() {
			return this.onHold;
		}
		
		public List<Object> getByUsers() {
			return this.byUsers;
		}
		
		public List<Object> getOriginalQuestions() {
			return this.originalQuestions;
		}
		
		@Override
		public String toString() {
			return "ClosedDetails [ "
				+ "reason = " + this.reason + ", "
				+ "description = " + this.description + ", "
				+ "onHold = " + this.onHold + ", "
				+ "byUsers = " + this.byUsers + ", "
				+ "originalQuestions = " + this.originalQuestions + ", "
				+ "]"; 
		}	
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MigratedTo {
	
		public MigratedTo(){}
	
		@JsonProperty("on_date") 
		private Integer onDate;
		
		@JsonProperty("question_id") 
		private Integer questionId;
		
		@JsonProperty("other_site") 
		private OtherSite otherSite;
		
		public Integer getOnDate() {
			return this.onDate;
		}
		
		public Integer getQuestionId() {
			return this.questionId;
		}
		
		public OtherSite getOtherSite() {
			return this.otherSite;
		}
		
		@Override
		public String toString() {
			return "MigratedTo [ "
				+ "onDate = " + this.onDate + ", "
				+ "questionId = " + this.questionId + ", "
				+ "otherSite = " + this.otherSite + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class OtherSite {
		
			public OtherSite(){}
		
			@JsonProperty("icon_url") 
			private String iconUrl;
			
			@JsonProperty("audience") 
			private String audience;
			
			@JsonProperty("favicon_url") 
			private String faviconUrl;
			
			@JsonProperty("site_type") 
			private String siteType;
			
			@JsonProperty("logo_url") 
			private String logoUrl;
			
			@JsonProperty("api_site_parameter") 
			private String apiSiteParameter;
			
			@JsonProperty("open_beta_date") 
			private Integer openBetaDate;
			
			@JsonProperty("site_state") 
			private String siteState;
			
			@JsonProperty("closed_beta_date") 
			private Integer closedBetaDate;
			
			@JsonProperty("site_url") 
			private String siteUrl;
			
			@JsonProperty("name") 
			private String name;
			
			@JsonProperty("high_resolution_icon_url") 
			private String highResolutionIconUrl;
			
			@JsonProperty("twitter_account") 
			private String twitterAccount;
			
			@JsonProperty("launch_date") 
			private Integer launchDate;
			
			@JsonProperty("styling") 
			private StylingInner stylingInner;
			
			@JsonProperty("aliases") 
			private List<Object> aliases = new ArrayList<Object>();
			
			@JsonProperty("related_sites") 
			private List<Object> relatedSites = new ArrayList<Object>();
			
			@JsonProperty("markdown_extensions") 
			private List<Object> markdownExtensions = new ArrayList<Object>();
			
			public String getIconUrl() {
				return this.iconUrl;
			}
			
			public String getAudience() {
				return this.audience;
			}
			
			public String getFaviconUrl() {
				return this.faviconUrl;
			}
			
			public String getSiteType() {
				return this.siteType;
			}
			
			public String getLogoUrl() {
				return this.logoUrl;
			}
			
			public String getApiSiteParameter() {
				return this.apiSiteParameter;
			}
			
			public Integer getOpenBetaDate() {
				return this.openBetaDate;
			}
			
			public String getSiteState() {
				return this.siteState;
			}
			
			public Integer getClosedBetaDate() {
				return this.closedBetaDate;
			}
			
			public String getSiteUrl() {
				return this.siteUrl;
			}
			
			public String getName() {
				return this.name;
			}
			
			public String getHighResolutionIconUrl() {
				return this.highResolutionIconUrl;
			}
			
			public String getTwitterAccount() {
				return this.twitterAccount;
			}
			
			public Integer getLaunchDate() {
				return this.launchDate;
			}
			
			public StylingInner getStylingInner() {
				return this.stylingInner;
			}
			
			public List<Object> getAliases() {
				return this.aliases;
			}
			
			public List<Object> getRelatedSites() {
				return this.relatedSites;
			}
			
			public List<Object> getMarkdownExtensions() {
				return this.markdownExtensions;
			}
			
			@Override
			public String toString() {
				return "OtherSite [ "
					+ "iconUrl = " + this.iconUrl + ", "
					+ "audience = " + this.audience + ", "
					+ "faviconUrl = " + this.faviconUrl + ", "
					+ "siteType = " + this.siteType + ", "
					+ "logoUrl = " + this.logoUrl + ", "
					+ "apiSiteParameter = " + this.apiSiteParameter + ", "
					+ "openBetaDate = " + this.openBetaDate + ", "
					+ "siteState = " + this.siteState + ", "
					+ "closedBetaDate = " + this.closedBetaDate + ", "
					+ "siteUrl = " + this.siteUrl + ", "
					+ "name = " + this.name + ", "
					+ "highResolutionIconUrl = " + this.highResolutionIconUrl + ", "
					+ "twitterAccount = " + this.twitterAccount + ", "
					+ "launchDate = " + this.launchDate + ", "
					+ "stylingInner = " + this.stylingInner + ", "
					+ "aliases = " + this.aliases + ", "
					+ "relatedSites = " + this.relatedSites + ", "
					+ "markdownExtensions = " + this.markdownExtensions + ", "
					+ "]"; 
			}	
			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class StylingInner {
			
				public StylingInner(){}
			
				@JsonProperty("tag_background_color") 
				private String tagBackgroundColor;
				
				@JsonProperty("link_color") 
				private String linkColor;
				
				@JsonProperty("tag_foreground_color") 
				private String tagForegroundColor;
				
				public String getTagBackgroundColor() {
					return this.tagBackgroundColor;
				}
				
				public String getLinkColor() {
					return this.linkColor;
				}
				
				public String getTagForegroundColor() {
					return this.tagForegroundColor;
				}
				
				@Override
				public String toString() {
					return "StylingInner [ "
						+ "tagBackgroundColor = " + this.tagBackgroundColor + ", "
						+ "linkColor = " + this.linkColor + ", "
						+ "tagForegroundColor = " + this.tagForegroundColor + ", "
						+ "]"; 
				}	
			}
			
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Notice {
	
		public Notice(){}
	
		@JsonProperty("owner_user_id") 
		private Integer ownerUserId;
		
		@JsonProperty("creation_date") 
		private Integer creationDate;
		
		@JsonProperty("body") 
		private String body;
		
		public Integer getOwnerUserId() {
			return this.ownerUserId;
		}
		
		public Integer getCreationDate() {
			return this.creationDate;
		}
		
		public String getBody() {
			return this.body;
		}
		
		@Override
		public String toString() {
			return "Notice [ "
				+ "ownerUserId = " + this.ownerUserId + ", "
				+ "creationDate = " + this.creationDate + ", "
				+ "body = " + this.body + ", "
				+ "]"; 
		}	
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Owner {
	
		public Owner(){}
	
		@JsonProperty("profile_image") 
		private String profileImage;
		
		@JsonProperty("user_type") 
		private String userType;
		
		@JsonProperty("user_id") 
		private Integer userId;
		
		@JsonProperty("link") 
		private String link;
		
		@JsonProperty("reputation") 
		private Integer reputation;
		
		@JsonProperty("display_name") 
		private String displayName;
		
		@JsonProperty("accept_rate") 
		private Integer acceptRate;
		
		@JsonProperty("badge_counts") 
		private BadgeCountsInnerInner badgeCountsInnerInner;
		
		public String getProfileImage() {
			return this.profileImage;
		}
		
		public String getUserType() {
			return this.userType;
		}
		
		public Integer getUserId() {
			return this.userId;
		}
		
		public String getLink() {
			return this.link;
		}
		
		public Integer getReputation() {
			return this.reputation;
		}
		
		public String getDisplayName() {
			return this.displayName;
		}
		
		public Integer getAcceptRate() {
			return this.acceptRate;
		}
		
		public BadgeCountsInnerInner getBadgeCountsInnerInner() {
			return this.badgeCountsInnerInner;
		}
		
		@Override
		public String toString() {
			return "Owner [ "
				+ "profileImage = " + this.profileImage + ", "
				+ "userType = " + this.userType + ", "
				+ "userId = " + this.userId + ", "
				+ "link = " + this.link + ", "
				+ "reputation = " + this.reputation + ", "
				+ "displayName = " + this.displayName + ", "
				+ "acceptRate = " + this.acceptRate + ", "
				+ "badgeCountsInnerInner = " + this.badgeCountsInnerInner + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class BadgeCountsInnerInner {
		
			public BadgeCountsInnerInner(){}
		
			@JsonProperty("gold") 
			private Integer gold;
			
			@JsonProperty("silver") 
			private Integer silver;
			
			@JsonProperty("bronze") 
			private Integer bronze;
			
			public Integer getGold() {
				return this.gold;
			}
			
			public Integer getSilver() {
				return this.silver;
			}
			
			public Integer getBronze() {
				return this.bronze;
			}
			
			@Override
			public String toString() {
				return "BadgeCountsInnerInner [ "
					+ "gold = " + this.gold + ", "
					+ "silver = " + this.silver + ", "
					+ "bronze = " + this.bronze + ", "
					+ "]"; 
			}	
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LastEditor {
	
		public LastEditor(){}
	
		@JsonProperty("profile_image") 
		private String profileImage;
		
		@JsonProperty("user_type") 
		private String userType;
		
		@JsonProperty("user_id") 
		private Integer userId;
		
		@JsonProperty("link") 
		private String link;
		
		@JsonProperty("reputation") 
		private Integer reputation;
		
		@JsonProperty("display_name") 
		private String displayName;
		
		@JsonProperty("accept_rate") 
		private Integer acceptRate;
		
		@JsonProperty("badge_counts") 
		private BadgeCounts badgeCounts;
		
		public String getProfileImage() {
			return this.profileImage;
		}
		
		public String getUserType() {
			return this.userType;
		}
		
		public Integer getUserId() {
			return this.userId;
		}
		
		public String getLink() {
			return this.link;
		}
		
		public Integer getReputation() {
			return this.reputation;
		}
		
		public String getDisplayName() {
			return this.displayName;
		}
		
		public Integer getAcceptRate() {
			return this.acceptRate;
		}
		
		public BadgeCounts getBadgeCounts() {
			return this.badgeCounts;
		}
		
		@Override
		public String toString() {
			return "LastEditor [ "
				+ "profileImage = " + this.profileImage + ", "
				+ "userType = " + this.userType + ", "
				+ "userId = " + this.userId + ", "
				+ "link = " + this.link + ", "
				+ "reputation = " + this.reputation + ", "
				+ "displayName = " + this.displayName + ", "
				+ "acceptRate = " + this.acceptRate + ", "
				+ "badgeCounts = " + this.badgeCounts + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class BadgeCounts {
		
			public BadgeCounts(){}
		
			@JsonProperty("gold") 
			private Integer gold;
			
			@JsonProperty("silver") 
			private Integer silver;
			
			@JsonProperty("bronze") 
			private Integer bronze;
			
			public Integer getGold() {
				return this.gold;
			}
			
			public Integer getSilver() {
				return this.silver;
			}
			
			public Integer getBronze() {
				return this.bronze;
			}
			
			@Override
			public String toString() {
				return "BadgeCounts [ "
					+ "gold = " + this.gold + ", "
					+ "silver = " + this.silver + ", "
					+ "bronze = " + this.bronze + ", "
					+ "]"; 
			}	
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MigratedFrom {
	
		public MigratedFrom(){}
	
		@JsonProperty("on_date") 
		private Integer onDate;
		
		@JsonProperty("question_id") 
		private Integer questionId;
		
		@JsonProperty("other_site") 
		private OtherSiteInner otherSiteInner;
		
		public Integer getOnDate() {
			return this.onDate;
		}
		
		public Integer getQuestionId() {
			return this.questionId;
		}
		
		public OtherSiteInner getOtherSiteInner() {
			return this.otherSiteInner;
		}
		
		@Override
		public String toString() {
			return "MigratedFrom [ "
				+ "onDate = " + this.onDate + ", "
				+ "questionId = " + this.questionId + ", "
				+ "otherSiteInner = " + this.otherSiteInner + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class OtherSiteInner {
		
			public OtherSiteInner(){}
		
			@JsonProperty("icon_url") 
			private String iconUrl;
			
			@JsonProperty("audience") 
			private String audience;
			
			@JsonProperty("favicon_url") 
			private String faviconUrl;
			
			@JsonProperty("site_type") 
			private String siteType;
			
			@JsonProperty("logo_url") 
			private String logoUrl;
			
			@JsonProperty("api_site_parameter") 
			private String apiSiteParameter;
			
			@JsonProperty("open_beta_date") 
			private Integer openBetaDate;
			
			@JsonProperty("site_state") 
			private String siteState;
			
			@JsonProperty("closed_beta_date") 
			private Integer closedBetaDate;
			
			@JsonProperty("site_url") 
			private String siteUrl;
			
			@JsonProperty("name") 
			private String name;
			
			@JsonProperty("high_resolution_icon_url") 
			private String highResolutionIconUrl;
			
			@JsonProperty("twitter_account") 
			private String twitterAccount;
			
			@JsonProperty("launch_date") 
			private Integer launchDate;
			
			@JsonProperty("styling") 
			private Styling styling;
			
			@JsonProperty("aliases") 
			private List<Object> aliases = new ArrayList<Object>();
			
			@JsonProperty("related_sites") 
			private List<Object> relatedSites = new ArrayList<Object>();
			
			@JsonProperty("markdown_extensions") 
			private List<Object> markdownExtensions = new ArrayList<Object>();
			
			public String getIconUrl() {
				return this.iconUrl;
			}
			
			public String getAudience() {
				return this.audience;
			}
			
			public String getFaviconUrl() {
				return this.faviconUrl;
			}
			
			public String getSiteType() {
				return this.siteType;
			}
			
			public String getLogoUrl() {
				return this.logoUrl;
			}
			
			public String getApiSiteParameter() {
				return this.apiSiteParameter;
			}
			
			public Integer getOpenBetaDate() {
				return this.openBetaDate;
			}
			
			public String getSiteState() {
				return this.siteState;
			}
			
			public Integer getClosedBetaDate() {
				return this.closedBetaDate;
			}
			
			public String getSiteUrl() {
				return this.siteUrl;
			}
			
			public String getName() {
				return this.name;
			}
			
			public String getHighResolutionIconUrl() {
				return this.highResolutionIconUrl;
			}
			
			public String getTwitterAccount() {
				return this.twitterAccount;
			}
			
			public Integer getLaunchDate() {
				return this.launchDate;
			}
			
			public Styling getStyling() {
				return this.styling;
			}
			
			public List<Object> getAliases() {
				return this.aliases;
			}
			
			public List<Object> getRelatedSites() {
				return this.relatedSites;
			}
			
			public List<Object> getMarkdownExtensions() {
				return this.markdownExtensions;
			}
			
			@Override
			public String toString() {
				return "OtherSiteInner [ "
					+ "iconUrl = " + this.iconUrl + ", "
					+ "audience = " + this.audience + ", "
					+ "faviconUrl = " + this.faviconUrl + ", "
					+ "siteType = " + this.siteType + ", "
					+ "logoUrl = " + this.logoUrl + ", "
					+ "apiSiteParameter = " + this.apiSiteParameter + ", "
					+ "openBetaDate = " + this.openBetaDate + ", "
					+ "siteState = " + this.siteState + ", "
					+ "closedBetaDate = " + this.closedBetaDate + ", "
					+ "siteUrl = " + this.siteUrl + ", "
					+ "name = " + this.name + ", "
					+ "highResolutionIconUrl = " + this.highResolutionIconUrl + ", "
					+ "twitterAccount = " + this.twitterAccount + ", "
					+ "launchDate = " + this.launchDate + ", "
					+ "styling = " + this.styling + ", "
					+ "aliases = " + this.aliases + ", "
					+ "relatedSites = " + this.relatedSites + ", "
					+ "markdownExtensions = " + this.markdownExtensions + ", "
					+ "]"; 
			}	
			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class Styling {
			
				public Styling(){}
			
				@JsonProperty("tag_background_color") 
				private String tagBackgroundColor;
				
				@JsonProperty("link_color") 
				private String linkColor;
				
				@JsonProperty("tag_foreground_color") 
				private String tagForegroundColor;
				
				public String getTagBackgroundColor() {
					return this.tagBackgroundColor;
				}
				
				public String getLinkColor() {
					return this.linkColor;
				}
				
				public String getTagForegroundColor() {
					return this.tagForegroundColor;
				}
				
				@Override
				public String toString() {
					return "Styling [ "
						+ "tagBackgroundColor = " + this.tagBackgroundColor + ", "
						+ "linkColor = " + this.linkColor + ", "
						+ "tagForegroundColor = " + this.tagForegroundColor + ", "
						+ "]"; 
				}	
			}
			
		}
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class BountyUser {
	
		public BountyUser(){}
	
		@JsonProperty("profile_image") 
		private String profileImage;
		
		@JsonProperty("user_type") 
		private String userType;
		
		@JsonProperty("user_id") 
		private Integer userId;
		
		@JsonProperty("link") 
		private String link;
		
		@JsonProperty("reputation") 
		private Integer reputation;
		
		@JsonProperty("display_name") 
		private String displayName;
		
		@JsonProperty("accept_rate") 
		private Integer acceptRate;
		
		@JsonProperty("badge_counts") 
		private BadgeCountsInner badgeCountsInner;
		
		public String getProfileImage() {
			return this.profileImage;
		}
		
		public String getUserType() {
			return this.userType;
		}
		
		public Integer getUserId() {
			return this.userId;
		}
		
		public String getLink() {
			return this.link;
		}
		
		public Integer getReputation() {
			return this.reputation;
		}
		
		public String getDisplayName() {
			return this.displayName;
		}
		
		public Integer getAcceptRate() {
			return this.acceptRate;
		}
		
		public BadgeCountsInner getBadgeCountsInner() {
			return this.badgeCountsInner;
		}
		
		@Override
		public String toString() {
			return "BountyUser [ "
				+ "profileImage = " + this.profileImage + ", "
				+ "userType = " + this.userType + ", "
				+ "userId = " + this.userId + ", "
				+ "link = " + this.link + ", "
				+ "reputation = " + this.reputation + ", "
				+ "displayName = " + this.displayName + ", "
				+ "acceptRate = " + this.acceptRate + ", "
				+ "badgeCountsInner = " + this.badgeCountsInner + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class BadgeCountsInner {
		
			public BadgeCountsInner(){}
		
			@JsonProperty("gold") 
			private Integer gold;
			
			@JsonProperty("silver") 
			private Integer silver;
			
			@JsonProperty("bronze") 
			private Integer bronze;
			
			public Integer getGold() {
				return this.gold;
			}
			
			public Integer getSilver() {
				return this.silver;
			}
			
			public Integer getBronze() {
				return this.bronze;
			}
			
			@Override
			public String toString() {
				return "BadgeCountsInner [ "
					+ "gold = " + this.gold + ", "
					+ "silver = " + this.silver + ", "
					+ "bronze = " + this.bronze + ", "
					+ "]"; 
			}	
		}
		
	}
	
}	
