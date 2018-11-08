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
		paletteContainer.add(createConfiguration1CreationTool());
		paletteContainer.add(createField2CreationTool());
		paletteContainer.add(createQueue3CreationTool());
		paletteContainer.add(createSink4CreationTool());
		paletteContainer.add(createSource5CreationTool());
		paletteContainer.add(createTask6CreationTool());
		paletteContainer.add(createTopic7CreationTool());
		paletteContainer.add(createType8CreationTool());
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
		paletteContainer.add(createInput2CreationTool());
		paletteContainer.add(createOutput3CreationTool());
		paletteContainer.add(createType4CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createConfiguration1CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Configuration1CreationTool_title,
				Messages.Configuration1CreationTool_desc,
				Collections.singletonList(CrossflowElementTypes.Configuration_2005));
		entry.setId("createConfiguration1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Configuration_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createField2CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(CrossflowElementTypes.Field_3001);
		types.add(CrossflowElementTypes.Field_2008);
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Field2CreationTool_title,
				Messages.Field2CreationTool_desc, types);
		entry.setId("createField2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Field_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createQueue3CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Queue3CreationTool_title,
				Messages.Queue3CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Queue_2002));
		entry.setId("createQueue3CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Queue_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSink4CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Sink4CreationTool_title,
				Messages.Sink4CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Sink_2004));
		entry.setId("createSink4CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Sink_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createSource5CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Source5CreationTool_title,
				Messages.Source5CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Source_2003));
		entry.setId("createSource5CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Source_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTask6CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Task6CreationTool_title,
				Messages.Task6CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Task_2006));
		entry.setId("createTask6CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Task_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createTopic7CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Topic7CreationTool_title,
				Messages.Topic7CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Topic_2001));
		entry.setId("createTopic7CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Topic_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	* @generated
	*/
	private ToolEntry createType8CreationTool() {
		DefaultNodeToolEntry entry = new DefaultNodeToolEntry(Messages.Type8CreationTool_title,
				Messages.Type8CreationTool_desc, Collections.singletonList(CrossflowElementTypes.Type_2007));
		entry.setId("createType8CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.Type_2007));
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
	private ToolEntry createInput2CreationTool() {
		DefaultLinkToolEntry entry = new DefaultLinkToolEntry(Messages.Input2CreationTool_title,
				Messages.Input2CreationTool_desc, Collections.singletonList(CrossflowElementTypes.TaskInput_4002));
		entry.setId("createInput2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(CrossflowElementTypes.getImageDescriptor(CrossflowElementTypes.TaskInput_4002));
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
