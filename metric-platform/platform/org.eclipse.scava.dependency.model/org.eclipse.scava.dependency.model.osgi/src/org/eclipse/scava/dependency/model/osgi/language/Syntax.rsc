@license{
	Copyright (c) 2018 Centrum Wiskunde & Informatica
	
	This program and the accompanying materials are made
	available under the terms of the Eclipse Public License 2.0
	which is available at https://www.eclipse.org/legal/epl-2.0/
	
	SPDX-License-Identifier: EPL-2.0
}
module org::eclipse::scava::dependency::model::osgi::language::Syntax


//--------------------------------------------------------------------------------
// Syntax
//--------------------------------------------------------------------------------

start syntax Manifest 
	= headers: Header* headers;

syntax Header
	= bundleSymbolicName: HeaderBundleSymbolicName bundleSymbolicName 
	| bundleVersion: HeaderBundleVersion bundleVersion
	| dynamicImportPackage : HeaderDynamicImportPackage dynamicImportPackage
	| exportPackage: HeaderExportPackage exportPackage
	| importPackage: HeaderImportPackage importPackage
	| requireBundle: HeaderRequireBundle requireBundle
	| customHeader: HeaderCustom
	;

syntax HeaderBundleSymbolicName
	= 'Bundle-SymbolicName' ':' QualifiedName name (';' {BundleSymbolicNameParameter ';'}+)?;

syntax BundleSymbolicNameParameter
	= singleton: 'singleton' ':=' Boolean singleton
	| fragmentAttachment: 'fragment-attachment' ':=' DirectiveFragmentAttachment fragmentAttachment
	| mandatory: 'mandatory' ':=' AttributeExpression mandatory
	;

syntax HeaderBundleVersion
	= 'Bundle-Version' ':' Version version;

syntax HeaderDynamicImportPackage
	= 'DynamicImport-Package' ':' {DynamicImportDescription ','}+ descriptions;
	
syntax DynamicImportDescription
	= {WildCardNames ';'}+ dynamicImports (';' {DynamicImportPackageParameter ';'}+)?;

syntax WildCardNames
	= {WildCardName ';'}+ wildCardNames;

syntax WildCardName
	= packageName: QualifiedName
	| packageWildCard: QualifiedName '.*'
	| globalWildCard: '*';

syntax DynamicImportPackageParameter
	= version: 'version' '=' QuotedHybridVersion version
	| bundleSymbolicName: 'bundle-symbolic-name' '=' QualifiedName bundleSymbolicName
	| bundleVersion: 'bundle-version' '=' QuotedHybridVersion bundleVersion;
	
syntax HeaderExportPackage
	= 'Export-Package' ':' {ExportPackage ','}+ packages;
	
syntax ExportPackage
	= {QualifiedName ';'}+ packageNames (';' {ExportPackageParameter ';'}+)?;
	
//Whes are the full and base parameters specified?
syntax ExportPackageParameter
	= base: 'base' '=' Boolean base
	| draft: 'draft' '=' Word draft 
	| exclude: 'exclude' ':=' {Name ','}+ exclude
	| full: 'full' '=' Boolean full
	| include: 'include' ':=' {Name ','}+ include
	| mandatory: 'mandatory' ':=' AttributeExpression mandatory
	| provenance: 'provenance' '=' Word provenance
	| specificationVersion: 'specification-version' '=' SimpleVersion specificationVersion
	| split: QualifiedName bundleSymbolicName '=' AttributeSplit split
	| status : 'status' '=' AttributeStatus status
	| uses: 'uses' ':='  HybridQualifiedName uses
	| version: 'version' ('='|':=') SimpleVersion version
	| xInternal: 'x-internal' ':=' Boolean xInternal
	| xFriends: 'x-friends' ':=' HybridQualifiedName xFriends
	;
	
syntax HeaderImportPackage
	= 'Import-Package' ':' {ImportPackage ','}+ packages;

syntax ImportPackage
	= {QualifiedName ';'}+ packageNames (';' {ImportPackageParameter ';'}+)?;

//Missing info about provenance and status parameters. Adding syntax based on data set.	
//Version has some exceptions in the data set (i.e. "" are not used) - Using UnrestrictedHybridVersion
//Using version (=|:=) - Exception found in the dataset
syntax ImportPackageParameter
	= bundleSymbolicName: 'bundle-symbolic-name' '=' QualifiedName bundleSymbolicName
	| bundleVersion: 'bundle-version' '=' HybridVersion bundleVersion
	| provenance: 'provenance' '=' Word provenance
	| resolution: 'resolution' ':=' DirectiveResolution resolution
	| specificationVersion: 'specification-version' '=' HybridVersion specificationVersion
	| split: QualifiedName bundleSymbolicName '=' AttributeSplit split
	| status : 'status' '=' AttributeStatus status
	| version: 'version' ('='|':=') UnrestrictedHybridVersion version
	| xInstallation : 'x-installation' ':=' DirectiveXInstallation xInstallation
	;
		
syntax HeaderRequireBundle
	= 'Require-Bundle' ':' {RequireBundle ','}+ bundles;

syntax RequireBundle
	= QualifiedName symbolicName (';' {RequireBundleParameter ';'}+ )? params;

syntax RequireBundleParameter
	= visibility: 'visibility' ':=' DirectiveVisibility visibility
	| resolution: 'resolution' ':=' DirectiveResolution resolution
	| xInstallation : 'x-installation' ':=' DirectiveXInstallation xInstallation
	| version: 'bundle-version' '=' QuotedHybridVersion version
	;

syntax HeaderCustom
	= CustomKey key ':' Value val;

syntax AttributeSplit
	= AttrSplitKeyword
	| QuotedAttrSplitKeyword
	;

syntax AttributeStatus
	= AttrStatusKeyword
	| QuotedAttrStatusKeyword
	;
	
syntax DirectiveFragmentAttachment
	= DirVisibilityKeyword
	| QuotedDirVisibilityKeyword
	;
	
syntax DirectiveResolution
	= DirResolutionKeyword
	| QuotedDirResolutionKeyword
	;
	
syntax DirectiveVisibility 
	= DirVisibilityKeyword
	| QuotedDirVisibilityKeyword
	; 

syntax DirectiveXInstallation
	= DirXInstallationKeyword
	| QuotedDirXInstallationKeyword
	;

syntax HybridVersion
	= Version 
	| VersionRange
	;

syntax QuotedHybridVersion
	= QuotedVersion 
	| VersionRange
	;

syntax UnrestrictedHybridVersion
	= QuotedVersion 
	| Version 
	| VersionRange
	;

syntax SimpleVersion
	= Version
	| QuotedVersion
	;
	
syntax VersionRange = [\"][(\[] Version floor [,] Version ceiling [\])][\"];

//See Manifest19
syntax ExceptionQualifiedName 
	= 'new' QualifiedName
	| QualifiedName
	;

syntax ExceptionMultipleQualifiedName = [\"] {ExceptionQualifiedName ','}+ [\"];	

syntax MultipleQualifiedName = [\"] {QualifiedName ','}+ [\"];	

syntax HybridQualifiedName 
	= ExceptionMultipleQualifiedName
//	| MultipleQualifiedName
	| QualifiedName
	;

syntax AttributeExpression
	= QuotedAttributes
	| Attribute
	;

syntax QuotedAttributes = [\"] {Attribute ','}+ [\"];


//--------------------------------------------------------------------------------
// Lexical
//--------------------------------------------------------------------------------	

//lexical Attribute = (AlphaNum | [_-.])+ !>> (AlphaNum | [_-.]);
//lexical Attribute 	= [a-zA-Z0-9\-_.]+ !>> [a-zA-Z0-9\-_.];
lexical Attribute 
	= reserved: ReservedAttribute
	| package: AttributePackage 
	;

lexical AttributePackage = QualifiedName \ ReservedAttribute;

lexical Alpha = [a-zA-Z];

lexical AlphaNum = (Alpha | Digit);

lexical AttrSplitKeyword = 'split';

lexical AttrStatusKeyword = 'provisional';

lexical Boolean = 'true' | 'false';

//case insensitive?	
lexical CustomKey = Key \ OSGiKey;

lexical Digit = [0-9];

lexical DirFragmentAttachKeyword
	= 'always' 
	| 'never' 
	| 'resolve-time'
	;

lexical DirResolutionKeyword
	= 'mandatory' 
	| 'optional';
	
lexical DirVisibilityKeyword
	= 'private' 
	| 'reexport'
	;

lexical DirXInstallationKeyword = 'greedy';

lexical Key = Alpha (AlphaNum | [\-_])+ !>> (AlphaNum | [\-_]);
//lexical Key = [a-zA-Z][a-zA-Z0-9\-_]+ !>> [a-zA-Z0-9\-_];

//lexical Name = (Alpha | [$_]) (AlphaNum | [$_])* !>> (AlphaNum | [$_]);
lexical Name = [a-zA-Z$_] [a-zA-Z0-9$_\-]* !>> [a-zA-Z0-9$_\-];

lexical Number = Digit+;

lexical QualifiedName = Name ([.] Name)* !>> Name;
//lexical QualifiedName = [a-zA-Z$_] [a-zA-Z0-9$_]* ([.] [a-zA-Z$_] [a-zA-Z0-9$_]*)* !>> [a-zA-Z0-9$_.];

lexical QuotedVersion = [\"] Version version [\"];

lexical QuotedAttrSplitKeyword = [\"] AttrSplitKeyword [\"] ;

lexical QuotedAttrStatusKeyword = [\"] AttrStatusKeyword [\"] ;

lexical QuotedDirFragmentAttachKeyword = [\"] DirFragmentAttachKeyword [\"];

lexical QuotedDirResolutionKeyword = [\"] DirResolutionKeyword [\"];

lexical QuotedDirVisibilityKeyword = [\"] DirVisibilityKeyword [\"];

lexical QuotedDirXInstallationKeyword = [\"] DirXInstallationKeyword [\"];

lexical Value = ![\n]+ !>> ![\n];

lexical Version = Number major ([.] Number minor ([.] Number micro ([.] (AlphaNum | [\-_])+ !>> (AlphaNum | [\-_]) qualifier)? )? )?;

lexical Word = AlphaNum+;


//--------------------------------------------------------------------------------
// Keywords
//--------------------------------------------------------------------------------

keyword OSGiKey
	= "Bundle-SymbolicName"
	| "Bundle-Version"
	| "DynamicImport-Package"
	| "Export-Package"
	| "Import-Package"
	| "Require-Bundle" 
	;

keyword ReservedAttribute 
	= bundleSymbolicName: "bundle-symbolic-name"
	| bundleVersion: "bundle-version"
	| resolution: "resolution"
	| provenance: "provenance"
	| specificationVersion: "specification-version"
	| status: "status"
	| version: "version"
	;


//--------------------------------------------------------------------------------
// Layout
//--------------------------------------------------------------------------------

layout Whitespace = [\t\n\r\ ]* !>> [\t\n\r\ ];