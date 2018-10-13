import { Git } from "./components/create-project/git.model";
import { Svn } from "./components/create-project/svn.model";
import { Bugzilla } from "./components/create-project/bugzilla.model";
import { SourceForge } from "./components/create-project/source-forge.model";
import { Redmine } from "./components/create-project/redmine.model";
import { NNTP } from "./components/create-project/nntp.model";
import { VersionControlSystems } from "./components/create-project/version-control-system.model";
import { IssueTrackingSystems } from "./components/create-project/issue-tracking-systems.model";
import { CommunicationChannels } from "./components/create-project/communication-channels.model";

export interface IProject {
    id?: number,
    url?: string,
    shortName? : string,
    name?: string,
    full_name?: string,
    _type?: string,
    description?: string,
    homePage?: string,
    vcsRepositories?: VersionControlSystems[],
    bts?: IssueTrackingSystems[],
    communication_channels?: CommunicationChannels[],
    git?: Git,
    svn?: Svn,
    bugzilla?: Bugzilla,
    sourceforge?: SourceForge,
    redmine?: Redmine,
    nntp?: NNTP,
    size?: string,
    downloadsUrl?: string,
    globalStatus?: string,
    hasTasks?: boolean
}

export class Project implements IProject {
    constructor(
        public id?: number,
        public url?: string,
        public shortName? : string,
        public name?: string,
        public full_name?: string,
        public _type?: string,
        public description?: string,
        public homePage?: string,
        public vcsRepositories?: VersionControlSystems[],
        public bts?: IssueTrackingSystems[],
        public communication_channels?: CommunicationChannels[],
        public git?: Git,
        public svn?: Svn,
        public bugzilla?: Bugzilla,
        public sourceforge?: SourceForge,
        public redmine?: Redmine,
        public nntp?: NNTP,
        public size?: string,
        public downloadsUrl?: string,
        public globalStatus?: string,
        public hasTasks?: boolean
    ) {
    }
}