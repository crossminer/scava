
/*
 * 
 */
package crossflow.diagram.part;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.tooling.runtime.part.DefaultLinkToolEntry;
import org.eclipse.gmf.tooling.runtime.part.DefaultNodeToolEntry;

import crossflow.diagram.providers.CrossflowElementTypes;

/**
 * @generated
 */
public class CrossflowPaletteFactory {

	/**
	* @generated
	*/
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createObjects1Group());
		paletteRoot.add(createConnections2Group());
	}

	/**
	* Creates "Objects" palette tool group
	* @generated
	*/
	private PaletteContainer createObjects1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Objects1Group_title);
		paletteContainer.setId("createObjects1Group"); //$NON-NLS-1$
		paletteContainer.add(createCommitmentTask1CreationTool());
		paletteContainer.add(createCsvSink2CreationTool());
		paletteContainer.add(createCsvSource3CreationTool());
		paletteContainer.add(createField4CreationTool());
		paletteContainer.add(createLanguage5CreationTool());
		paletteContainer.add(createOpinionatedTask6CreationTool());
		paletteContainer.add(createParameter7CreationTool());
		paletteContainer.add(createQueue8CreationTool());
		paletteContainer.add(createSink9CreationTool());
		paletteContainer.add(createSource10CreationTool());
		paletteContainer.add(createTask11CreationTool());
		paletteContainer.add(createTopic12CreationTool());
		paletteContainer.add(createType13CreationTool());
		return paletteContainer;
	}

	/**
	* Creates "Connections" palette tool group
	* @generated
	*/
	private PaletteContainer createConnections2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Connections2Group_title);
		paletteContainer.setId("createConnections2Group"); //$NON-NLS-1$
		paletteContainer.add(createExtending1CreationTool());
		paletteContainer.add(createInputOf2CreationTool());
		paletteContainer.add(createOutput3CreationTool());
		paletteContainer.add(createType4CreationTool());
		return paletteContainer;
	}

	/**
	* @generated
	*/
	private ToolEntry createCommitmentTask1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CommitmentTask1CreationTool_title,
				Messages.CommitmentTask1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.CommitmentTask_2007));
		entry.setId("createCommitmentTask1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CommitmentTask_2007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSink2CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSink2CreationTool_title,
				Messages.CsvSink2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSink_2002));
		entry.setId("createCsvSink2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSink_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createCsvSource3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.CsvSource3CreationTool_title,
				Messages.CsvSource3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.CsvSource_2001));
		entry.setId("createCsvSource3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.CsvSource_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createField4CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(CrossflowElementTypes.Field_3001);
		types.add(CrossflowElementTypes.Field_2014);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Field4CreationTool_title,
				Messages.Field4CreationTool_desc, types);
		entry.setId("createField4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Field_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createLanguage5CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Language5CreationTool_title,
				Messages.Language5CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Language_2013));
		entry.setId("createLanguage5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Language_2013));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createOpinionatedTask6CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.OpinionatedTask6CreationTool_title,
				Messages.OpinionatedTask6CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.OpinionatedTask_2008));
		entry.setId("createOpinionatedTask6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.OpinionatedTask_2008));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createParameter7CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Parameter7CreationTool_title,
				Messages.Parameter7CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Parameter_3002));
		entry.setId("createParameter7CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Parameter_3002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createQueue8CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Queue8CreationTool_title,
				Messages.Queue8CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Queue_2004));
		entry.setId("createQueue8CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Queue_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSink9CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Sink9CreationTool_title,
				Messages.Sink9CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Sink_2006));
		entry.setId("createSink9CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Sink_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSource10CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Source10CreationTool_title,
				Messages.Source10CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Source_2005));
		entry.setId("createSource10CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Source_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTask11CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Task11CreationTool_title,
				Messages.Task11CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Task_2010));
		entry.setId("createTask11CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Task_2010));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTopic12CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Topic12CreationTool_title,
				Messages.Topic12CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Topic_2003));
		entry.setId("createTopic12CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Topic_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType13CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Type13CreationTool_title,
				Messages.Type13CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Type_2011));
		entry.setId("createType13CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Type_2011));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createExtending1CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Extending1CreationTool_title,
				Messages.Extending1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.TypeExtending_4004));
		entry.setId("createExtending1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TypeExtending_4004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createInputOf2CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.InputOf2CreationTool_title,
				Messages.InputOf2CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.StreamInputOf_4005));
		entry.setId("createInputOf2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.StreamInputOf_4005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createOutput3CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Output3CreationTool_title,
				Messages.Output3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.TaskOutput_4003));
		entry.setId("createOutput3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TaskOutput_4003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType4CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Type4CreationTool_title,
				Messages.Type4CreationTool_desc, Collections.singletonList(CrossflowElementTypes.StreamType_4001));
		entry.setId("createType4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.StreamType_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

}
