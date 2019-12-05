

function initializeCoreMod() {
    return {
        'drops': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.block.Block',
                'methodName': 'func_220076_a', // getDrops
                'methodDesc': '(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/storage/loot/LootContext$Builder;)Ljava/util/List;'
            },
            'transformer': function(method) {
              print('[RandomEnchants]: Patching Minecraft\' Block#getDrops');

                var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');

                var newInstructions = new InsnList();
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 2));

                newInstructions.add(ASM.buildMethodCall(
                    "com/tfar/randomenchants/util/CoremodHooks",
                    "drops",
                    "(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/storage/loot/LootContext$Builder;)Ljava/util/List;",
                    ASM.MethodType.STATIC
                ));

                newInstructions.add(new InsnNode(Opcodes.ARETURN));
                method.instructions.insertBefore(method.instructions.getFirst(), newInstructions);

                return method;
            }
        }
     }
  }