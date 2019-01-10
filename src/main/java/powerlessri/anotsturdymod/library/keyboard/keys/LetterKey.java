package powerlessri.anotsturdymod.library.keyboard.keys;

import com.google.common.base.MoreObjects;
import net.minecraft.util.IStringSerializable;
import org.lwjgl.input.Keyboard;
import powerlessri.anotsturdymod.library.keyboard.Key;

public enum LetterKey implements Key, IStringSerializable {

    A(Keyboard.KEY_A),
    B(Keyboard.KEY_B),
    C(Keyboard.KEY_C),
    D(Keyboard.KEY_D),
    E(Keyboard.KEY_E),
    F(Keyboard.KEY_F),
    G(Keyboard.KEY_G),
    H(Keyboard.KEY_H),
    I(Keyboard.KEY_I),
    J(Keyboard.KEY_J),
    K(Keyboard.KEY_K),
    L(Keyboard.KEY_L),
    M(Keyboard.KEY_M),
    N(Keyboard.KEY_N),
    O(Keyboard.KEY_O),
    P(Keyboard.KEY_P),
    Q(Keyboard.KEY_Q),
    R(Keyboard.KEY_R),
    S(Keyboard.KEY_S),
    T(Keyboard.KEY_T),
    U(Keyboard.KEY_U),
    V(Keyboard.KEY_V),
    W(Keyboard.KEY_W),
    X(Keyboard.KEY_X),
    Y(Keyboard.KEY_Y),
    Z(Keyboard.KEY_Z),
    NUM_0(Keyboard.KEY_0),
    NUM_1(Keyboard.KEY_1),
    NUM_2(Keyboard.KEY_2),
    NUM_3(Keyboard.KEY_3),
    NUM_4(Keyboard.KEY_4),
    NUM_5(Keyboard.KEY_5),
    NUM_6(Keyboard.KEY_6),
    NUM_7(Keyboard.KEY_7),
    NUM_8(Keyboard.KEY_8),
    NUM_9(Keyboard.KEY_9);

    public final String name;
    public final int keyCode;

    private LetterKey(int keyCode) {
        this.keyCode = keyCode;
        this.name = this.name().toLowerCase();
    }


    public int getKeyCode() {
        return keyCode;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("keyCode", keyCode)
                .toString();
    }

}
