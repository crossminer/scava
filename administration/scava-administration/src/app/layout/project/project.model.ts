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
    name?: string,
    description?: string,
    homepage?: string,
    vcs?: VersionControlSystems[],
    bts?: IssueTrackingSystems[],
    communication_channels?: CommunicationChannels[],
    git?: Git,
    svn?: Svn,
    bugzilla?: Bugzilla,
    sourceforge?: SourceForge,
    redmine?: Redmine,
    nntp?: NNTP
}

export class Project implements IProject {
    constructor(
        public id?: number,
        public url?: string,
        public name?: string,
        public description?: string,
        public homepage?: string,
        public vcs?: VersionControlSystems[],
        public bts?: IssueTrackingSystems[],
        public communication_channels?: CommunicationChannels[],
        public git?: Git,
        public svn?: Svn,
        public bugzilla?: Bugzilla,
        public sourceforge?: SourceForge,
        public redmine?: Redmine,
        public nntp?: NNTP
    ) {
    }
}